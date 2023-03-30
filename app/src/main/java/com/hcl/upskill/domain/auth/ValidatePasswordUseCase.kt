package com.hcl.upskill.domain.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

     suspend fun execute(password: String?): Flow<Boolean> = flow {
        emit(password?.isNotEmpty()?:false)
    }
}