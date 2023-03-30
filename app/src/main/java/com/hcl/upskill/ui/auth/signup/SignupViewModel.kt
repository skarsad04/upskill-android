package com.hcl.upskill.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.auth.*
import com.hcl.upskill.model.request.auth.SignupModel
import com.hcl.upskill.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel
      @Inject constructor(private val dispatcher: BaseDispatcher,
                          private val validateFirstNameUseCase: ValidateFirstNameUseCase,
                          private val validateLastNameUseCase: ValidateLastNameUseCase,
                          private val validateEmailUseCase: ValidateEmailUseCase,
                          private val validateUserNameUseCase: ValidateUserNameUseCase,
                          private val validatePasswordUseCase: ValidatePasswordUseCase,
                          private val makeSignUpUseCase: MakeSignUpUseCase) : ViewModel() {

    private val _validFirstName = MutableStateFlow(true)
    val validFirstName get() = _validFirstName.asStateFlow()


    private val _validLastName = MutableStateFlow(true)
    val validLastName get() = _validLastName.asStateFlow()

    private val _validEmail = MutableStateFlow(true)
    val validEmail get() = _validEmail.asStateFlow()

    private val _validUsername = MutableStateFlow(true)
    val validUsername get() = _validUsername.asStateFlow()


    private val _validPassword = MutableStateFlow(true)
    val validPassword get() = _validPassword.asStateFlow()


    private val _signup = MutableStateFlow<NetworkResult<Any?>>(NetworkResult.Default)
    val signup get() = _signup.asStateFlow()


    fun makeSignup(model: SignupModel?) {

        viewModelScope.launch(dispatcher.io) {
            try {

                // First Name
                var isValidFirstName = false
                validateFirstNameUseCase.execute(model?.first_name).collectLatest {
                    _validFirstName.value = it
                    isValidFirstName = it
                }
                if(!isValidFirstName) return@launch

                // First Name
                var isValidLastName = false
                validateLastNameUseCase.execute(model?.last_name).collectLatest {
                    _validLastName.value = it
                    isValidLastName = it
                }
                if(!isValidLastName) return@launch


                // Email Validation
                var isValidEmail = false
                validateEmailUseCase.execute(model?.email).collectLatest {
                    _validEmail.value = it
                    isValidEmail = it
                }
                if(!isValidEmail) return@launch


                // Username Validation
                var isValidUsername = false
                validateUserNameUseCase.execute(model?.username).collectLatest {
                    _validUsername.value = it
                    isValidUsername = it
                }
                if(!isValidUsername) return@launch

                // Password Validation
                var isValidPassword = false
                validatePasswordUseCase.execute(model?.password).collectLatest {
                    _validPassword.value = it
                    isValidPassword = it
                }
                if(!isValidPassword) return@launch

                // Signup Validation
                makeSignUpUseCase.execute(model).collectLatest { _signup.value = it }

            } catch (_: Exception) {
            }
        }
    }





                          }