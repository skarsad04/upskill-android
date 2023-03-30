package com.hcl.upskill.domain.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateLastNameUseCase @Inject constructor() {

    suspend fun execute(lastName: String?): Flow<Boolean> = flow {
        emit(lastName?.isNotEmpty()?:false)
    }
}