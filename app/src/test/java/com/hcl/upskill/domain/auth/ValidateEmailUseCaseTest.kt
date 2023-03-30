@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.domain.auth

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.junit.Test
import kotlinx.coroutines.test.runTest

class ValidateEmailUseCaseTest {

    private val useCase = ValidateEmailUseCase()


    @Test
    fun `Empty email return false`() = runTest {
        useCase.execute("").collectLatest {
            assertEquals(false, it)
        }
    }


    @Test
    fun `Incomplete email return false`() = runTest {
        useCase.execute("someEmail").collectLatest {
            assertEquals(false, it)
        }
    }

    @Test
    fun `Incomplete email without com return false`() = runTest {
        useCase.execute("someEmail@gmail.").collectLatest {
            assertEquals(false, it)
        }
    }


    @Test
    fun `valid email return true`() = runTest {
        useCase.execute("someEmail@gmail.com").collectLatest {
            assertEquals(true, it)
        }
    }

}