package com.hcl.upskill.domain.auth

import com.hcl.upskill.model.request.auth.LoginModel
import com.hcl.upskill.ui.auth.login.LoginRepository
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeLoginUseCase @Inject constructor(private val repository: LoginRepository) {

   suspend fun execute(model: LoginModel?) : Flow<NetworkResult<Any?>> {
       return repository.login(model)
    }
}