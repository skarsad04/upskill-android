package com.hcl.upskill.domain.auth

import androidx.core.util.PatternsCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {

    suspend fun execute(userName : String?): Flow<Boolean> = flow {
        emit(userName?.isNotEmpty()?:false)
    }
}