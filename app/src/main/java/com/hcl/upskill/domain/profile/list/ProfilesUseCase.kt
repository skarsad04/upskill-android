package com.hcl.upskill.domain.profile.list

import com.hcl.upskill.ui.profile.listing.ProfilesRepository
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfilesUseCase @Inject constructor(private val repository: ProfilesRepository) {

    suspend fun execute(token: String?) : Flow<NetworkResult<Any?>> {
        return repository.profiles(token)
    }
}