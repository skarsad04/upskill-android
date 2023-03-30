@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.viewModel.auth

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.auth.MakeLoginUseCase
import com.hcl.upskill.domain.auth.ValidateEmailUseCase
import com.hcl.upskill.domain.auth.ValidatePasswordUseCase
import com.hcl.upskill.model.request.auth.LoginModel
import com.hcl.upskill.model.response.LoginResponseModel
import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.ui.auth.login.LoginRepository
import com.hcl.upskill.ui.auth.login.LoginViewModel
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import net.bytebuddy.implementation.MethodCall.invoke
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    // ViewModel
    private lateinit var viewModel : LoginViewModel

    // Domain
    private var validateEmailUseCase = ValidateEmailUseCase()
    private var validatePasswordUseCase = ValidatePasswordUseCase()
    private lateinit var makeLoginUseCase : MakeLoginUseCase

    // Repository
    lateinit var repository: LoginRepository

    // Up Skill API
    @Mock lateinit var apiSkill: Skill


    // Dispatcher
    private val baseDispatcher = BaseDispatcher()
    private val dispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)

        repository = LoginRepository(apiSkill)
        makeLoginUseCase = MakeLoginUseCase(repository)

        viewModel =  LoginViewModel(dispatcher = baseDispatcher,
                                    validateEmailUseCase = validateEmailUseCase,
                                    validatePasswordUseCase = validatePasswordUseCase,
                                    makeLoginUseCase = makeLoginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
     fun `Login with empty email return false`() = runTest  {
        // given
        val data = LoginModel(username = "", password = "")
        
        // when
        viewModel.makeLogin(data)
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validateEmailUseCase.execute(data.username).collectLatest {
            assertEquals(false, viewModel.validEmail.value)
        }
    }



    @Test
    fun `Login with invalid email return false`() = runTest {
        // given
        val data = LoginModel(username = "someEmail@gmail.", password = "")

        // when
        viewModel.makeLogin(data)
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validateEmailUseCase.execute(data.username).collectLatest {
            assertEquals(false, viewModel.validEmail.value)
        }
    }


    @Test
    fun `Login with empty password return false`() = runTest {
        // given
        val data = LoginModel(username = "someEmail@gmail.com", password = "")

        // when
        viewModel.makeLogin(data)
        validateEmailUseCase.execute(data.username).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validatePasswordUseCase.execute(data.password).collectLatest {
            assertEquals(false, viewModel.validPassword.value)
        }
    }


    @Test
    fun `Login with email and password with success return true`() = runTest {
        // given
        val data = LoginModel(username = "atanvir266@gmail.com", password = "12345")

        // when
        viewModel.makeLogin(data)
        validateEmailUseCase.execute(data.username).collect()
        validatePasswordUseCase.execute(data.password).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        makeLoginUseCase.execute(data).collectLatest {
            async {
                assertEquals(true,viewModel.validEmail.value)
                assertEquals(true,viewModel.validPassword.value)
                assertNotNull(viewModel.login.value)
                assertEquals(false,viewModel.login.value is NetworkResult.Loading)
                delay(2000)
                assertEquals(true,viewModel.login.value is NetworkResult.Success)

            }.join()

        }
    }

}