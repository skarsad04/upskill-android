@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.domain.auth

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test
class ValidateLastNameTest {

    private val useCase = ValidateLastNameUseCase()

    @Test
    fun `Empty last name return false`() = runTest {
        useCase.execute("").collectLatest {
            assertEquals(false,it)
        }
    }

    @Test
    fun `correct last name return true`() = runTest {
        useCase.execute("Ahmed").collectLatest {
            assertEquals(true,it)
        }
    }
}