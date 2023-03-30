package com.hcl.upskill.ui.profile.detail

import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileDetailRepository @Inject constructor(private val apiSkill: Skill) {

    suspend fun profileDetail(token : String?) = flow<NetworkResult<Any?>> {
        emit(NetworkResult.Loading)
        val response = apiSkill.profileDetail(token)
        emit(NetworkResult.Success(response))
    }.catch {
        emit(NetworkResult.Error(it.message?:"Unknown"))
    }
}