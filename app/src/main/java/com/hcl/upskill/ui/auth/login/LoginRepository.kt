package com.hcl.upskill.ui.auth.login

import com.hcl.upskill.model.request.auth.LoginModel
import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiSkills: Skill?) {

   suspend fun login(model : LoginModel?) : Flow<NetworkResult<Any?>> = flow {
        emit(NetworkResult.Loading)
        val response = apiSkills?.login(model)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Error(e.message?:"Unknown"))
    }

}