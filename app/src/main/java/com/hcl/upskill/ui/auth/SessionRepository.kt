package com.hcl.upskill.ui.auth

import com.hcl.upskill.model.request.auth.SessionRequestModel
import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class SessionRepository @Inject constructor(private val apiSkill: Skill?) {

    suspend fun refreshToken(token: String?,model : SessionRequestModel?) : Flow<NetworkResult<Any?>> = flow {
        emit(NetworkResult.Loading)
        val response = apiSkill?.refreshToken(token,model)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Error(e.message?:"Unknown"))
    }

}