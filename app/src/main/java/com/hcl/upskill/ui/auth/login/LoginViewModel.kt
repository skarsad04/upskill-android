package com.hcl.upskill.ui.auth.login

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.auth.ValidateEmailUseCase
import com.hcl.upskill.domain.auth.MakeLoginUseCase
import com.hcl.upskill.domain.auth.ValidatePasswordUseCase
import com.hcl.upskill.model.request.auth.LoginModel
import com.hcl.upskill.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
      @Inject constructor(private val dispatcher: BaseDispatcher,
                          private val validateEmailUseCase: ValidateEmailUseCase,
                          private val validatePasswordUseCase: ValidatePasswordUseCase,
                          private val makeLoginUseCase: MakeLoginUseCase) : ViewModel() {

    private val _validEmail = MutableStateFlow(true)
    val validEmail get() = _validEmail.asStateFlow()

    private val _validPassword = MutableStateFlow(true)
    val validPassword get() = _validPassword.asStateFlow()


    private val _login = MutableStateFlow<NetworkResult<Any?>>(NetworkResult.Default)
    val login get() = _login.asStateFlow()


    fun makeLogin(model: LoginModel?) {

        viewModelScope.launch(dispatcher.io) {
            try {

                // Email Validation
                var isValidEmail = false
                validateEmailUseCase.execute(model?.username).collectLatest {
                    _validEmail.value = it
                    isValidEmail = it
                }
                if(!isValidEmail) return@launch

                // Password Validation
                var isValidPassword = false
                validatePasswordUseCase.execute(model?.password).collectLatest {
                    _validPassword.value = it
                    isValidPassword = it
                }
                if(!isValidPassword) return@launch

                // Login Validation
                makeLoginUseCase.execute(model).collectLatest { _login.value = it }

            } catch (e: Exception) {
               // Log.e("exception",e?.message?:"")
            }
        }
    }
    }

