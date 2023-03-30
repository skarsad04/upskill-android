package com.hcl.upskill.ui.profile.listing

import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfilesRepository @Inject constructor(private val apiSkill: Skill) {

    suspend fun profiles(token : String?) = flow<NetworkResult<Any?>> {
        emit(NetworkResult.Loading)
        val response = apiSkill.profiles(token)
        emit(NetworkResult.Success(response))
    }.catch {
        emit(NetworkResult.Error(it.message?:"Unknown"))
    }
}