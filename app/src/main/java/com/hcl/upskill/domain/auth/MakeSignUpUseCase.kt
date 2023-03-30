package com.hcl.upskill.domain.auth

import com.hcl.upskill.model.request.auth.SignupModel
import com.hcl.upskill.ui.auth.signup.SignupRepository
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeSignUpUseCase @Inject constructor(private val repository: SignupRepository) {

    suspend fun execute(model: SignupModel?) : Flow<NetworkResult<Any?>> {
       return repository.makeSignup(model)
    }
}