package com.hcl.upskill.domain.profile.detail

import com.hcl.upskill.ui.profile.detail.ProfileDetailRepository
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileDetailUseCase @Inject constructor(private val repository: ProfileDetailRepository) {

    suspend fun execute(token: String?) : Flow<NetworkResult<Any?>> {
        return repository.profileDetail(token)
    }
}