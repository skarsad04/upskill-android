package com.hcl.upskill.domain.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateFirstNameUseCase @Inject constructor() {

    suspend fun execute(firstName: String?): Flow<Boolean> = flow {
        emit(firstName?.isNotEmpty()?:false)
    }
}