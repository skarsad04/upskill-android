package com.hcl.upskill.domain.auth

import androidx.core.util.PatternsCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    suspend fun execute(email : String?): Flow<Boolean> = flow {
        emit(PatternsCompat.EMAIL_ADDRESS.matcher(email?:"").matches())
    }

}