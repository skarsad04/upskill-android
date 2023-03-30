@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.domain.auth

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ValidateUserNameTest {

    private val useCase = ValidateUserNameUseCase()

    @Test
    fun `Empty username return false`() = runTest {
        useCase.execute("").collectLatest {
            assertEquals(false,it)
        }
    }

    @Test
    fun `correct username return true`() = runTest {
        useCase.execute("atanvir266").collectLatest {
            assertEquals(true,it)
        }
    }
}