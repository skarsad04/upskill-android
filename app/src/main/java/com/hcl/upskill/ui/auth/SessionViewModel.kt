package com.hcl.upskill.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.auth.MakeLoginUseCase
import com.hcl.upskill.domain.auth.MakeSessionUseCase
import com.hcl.upskill.domain.auth.ValidateEmailUseCase
import com.hcl.upskill.domain.auth.ValidatePasswordUseCase
import com.hcl.upskill.model.request.auth.SessionRequestModel
import com.hcl.upskill.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SessionViewModel
      @Inject constructor(private val dispatcher: BaseDispatcher,
                          private val makeSessionUseCase: MakeSessionUseCase) : ViewModel() {

    private val _session = MutableStateFlow<NetworkResult<Any?>>(NetworkResult.Default)
    val session get() = _session.asStateFlow()

    fun makeSession(token: String?,model : SessionRequestModel?) {
        viewModelScope.launch(dispatcher.io) {
            try {
                makeSessionUseCase.execute(token,model)?.collectLatest { _session.value = it }
            }catch (_:Exception){ }
        }

    }

}