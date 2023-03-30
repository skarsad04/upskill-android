package com.hcl.upskill.ui.profile.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.profile.list.ProfilesUseCase
import com.hcl.upskill.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilesViewModel
      @Inject constructor(private val dispatcher: BaseDispatcher,
                          private val profilesUseCase: ProfilesUseCase) : ViewModel() {




    private val _profile = MutableStateFlow<NetworkResult<Any?>>(NetworkResult.Default)
    val profile get() = _profile.asStateFlow()


    fun profile(token: String?) {
        viewModelScope.launch(dispatcher.io) {
            profilesUseCase.execute(token).collectLatest { _profile.value = it  }
        }
    }


}