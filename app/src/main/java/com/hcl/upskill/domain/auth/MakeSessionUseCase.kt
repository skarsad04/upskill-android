package com.hcl.upskill.domain.auth

import com.hcl.upskill.model.request.auth.SessionRequestModel
import com.hcl.upskill.ui.auth.SessionRepository
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeSessionUseCase @Inject constructor(private val repository: SessionRepository?) {

   suspend fun execute(token: String?,model: SessionRequestModel?) : Flow<NetworkResult<Any?>>?{
    return repository?.refreshToken(token,model)
   }
}