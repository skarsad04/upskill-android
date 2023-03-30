@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.domain.auth

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ValidateFirstNameTest {

    private val useCase = ValidateFirstNameUseCase()

    @Test
    fun `Empty first name return false`() = runTest {
        useCase.execute("").collectLatest {
            assertEquals(false,it)
        }
    }

    @Test
    fun `correct first name return true`() = runTest {
        useCase.execute("Tanvir").collectLatest {
            assertEquals(true,it)
        }
    }
}