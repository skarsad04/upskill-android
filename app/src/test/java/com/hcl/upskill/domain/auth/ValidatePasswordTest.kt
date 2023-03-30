@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.domain.auth

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ValidatePasswordTest {

    private val useCase = ValidatePasswordUseCase()

    @Test
    fun `Empty password return false`() = runTest {
        useCase.execute("").collectLatest {
            assertEquals(false,it)
        }
    }

    @Test
    fun `correct password return true`() = runTest {
        useCase.execute("12345").collectLatest {
            assertEquals(true,it)
        }
    }
}
