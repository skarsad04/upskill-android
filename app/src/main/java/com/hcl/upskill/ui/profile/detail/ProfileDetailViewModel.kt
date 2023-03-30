package com.hcl.upskill.ui.profile.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.di.NetworkModule
import com.hcl.upskill.domain.profile.detail.ProfileDetailUseCase
import com.hcl.upskill.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel
      @Inject constructor(private val dispatcher: BaseDispatcher,
                          private val profileDetailUseCase: ProfileDetailUseCase) : ViewModel() {




    private val _profileDetail = MutableStateFlow<NetworkResult<Any?>>(NetworkResult.Default)
    val profileDetail get() = _profileDetail.asStateFlow()


    fun profileDetail(token: String?) {
        viewModelScope.launch(dispatcher.io) {
            profileDetailUseCase.execute(token).collectLatest { _profileDetail.value = it  }
        }
    }


}