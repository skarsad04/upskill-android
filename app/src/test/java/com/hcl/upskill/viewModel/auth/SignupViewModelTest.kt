@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.viewModel.auth

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.auth.*
import com.hcl.upskill.model.request.auth.SignupModel
import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.ui.auth.signup.SignupRepository
import com.hcl.upskill.ui.auth.signup.SignupViewModel
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SignupViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    // ViewModel
    private lateinit var viewModel : SignupViewModel

    // Domain
    private var validateFirstNameUseCase = ValidateFirstNameUseCase()
    private var validateLastNameUseCase = ValidateLastNameUseCase()
    private var validateEmailUseCase = ValidateEmailUseCase()
    private var validateUserNameUseCase = ValidateUserNameUseCase()
    private var validatePasswordUseCase = ValidatePasswordUseCase()
    private lateinit var makeSignUpUseCase : MakeSignUpUseCase

    // Repository
    private lateinit var repository: SignupRepository

    // Up Skill API
    @Mock lateinit var apiSkill: Skill


    // Dispatcher
    private val baseDispatcher = BaseDispatcher()
    private val dispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)

        repository = SignupRepository(apiSkill)
        makeSignUpUseCase = MakeSignUpUseCase(repository)


        viewModel =  SignupViewModel(dispatcher = baseDispatcher,
                                    validateFirstNameUseCase = validateFirstNameUseCase,
                                    validateLastNameUseCase = validateLastNameUseCase,
                                    validateEmailUseCase = validateEmailUseCase,
                                    validateUserNameUseCase = validateUserNameUseCase,
                                    validatePasswordUseCase = validatePasswordUseCase,
                                    makeSignUpUseCase = makeSignUpUseCase)
    }

    @Test
    fun `Signup up with empty first name return false`() = runTest {
        // given
        val data = SignupModel(first_name = "", last_name = "", email = "",
                               username = "", password = "", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        dispatcher.scheduler.advanceUntilIdle()

        // than
        validateFirstNameUseCase.execute(data.first_name).collectLatest {
            assertEquals(false, viewModel.validFirstName.value)
        }
    }


    @Test
    fun `Signup up with empty last name return false`() = runTest {
        // given
        val data = SignupModel(first_name = "Tanvir", last_name = "", email = "",
                               username = "", password = "", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        validateFirstNameUseCase.execute(data.first_name).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validateLastNameUseCase.execute(data.last_name).collectLatest {
            async {
                assertEquals(true,viewModel.validFirstName.value)
                assertEquals(false, viewModel.validLastName.value)
            }.join()
        }
    }



    @Test
    fun `Signup up with empty email return false`() = runTest {
        // given
        val data = SignupModel(first_name = "Tanvir", last_name = "Ahmed", email = "",
                               username = "", password = "", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        validateFirstNameUseCase.execute(data.first_name).collect()
        validateLastNameUseCase.execute(data.last_name).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validateEmailUseCase.execute(data.email).collectLatest {
            async {
                assertEquals(true,viewModel.validFirstName.value)
                assertEquals(true, viewModel.validLastName.value)
                assertEquals(false, viewModel.validEmail.value)
            }.join()
        }
    }

    @Test
    fun `Signup up with invalid email return false`() = runTest {
        // given
        val data = SignupModel(first_name = "Tanvir", last_name = "Ahmed", email = "someEmail@gmail.",
                               username = "", password = "", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        validateFirstNameUseCase.execute(data.first_name).collect()
        validateLastNameUseCase.execute(data.last_name).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validateEmailUseCase.execute(data.email).collectLatest {
            async {
                assertEquals(true,viewModel.validFirstName.value)
                assertEquals(true, viewModel.validLastName.value)
                assertEquals(false, viewModel.validEmail.value)
            }.join()
        }
    }



    @Test
    fun `Signup up with empty username return false`() = runTest {
        // given
        val data = SignupModel(first_name = "Tanvir", last_name = "Ahmed", email = "atanvir266@gmail.com",
                               username = "", password = "", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        validateFirstNameUseCase.execute(data.first_name).collect()
        validateLastNameUseCase.execute(data.last_name).collect()
        validateEmailUseCase.execute(data.email).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validateUserNameUseCase.execute(data.username).collectLatest {
            async {
                assertEquals(true,viewModel.validFirstName.value)
                assertEquals(true, viewModel.validLastName.value)
                assertEquals(true, viewModel.validEmail.value)
                assertEquals(false, viewModel.validUsername.value)
            }.join()
        }
    }


    @Test
    fun `Signup up with empty password return false`() = runTest {
        // given
        val data = SignupModel(first_name = "Tanvir", last_name = "Ahmed", email = "atanvir266@gmail.com",
                               username = "atanvir266", password = "", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        validateFirstNameUseCase.execute(data.first_name).collect()
        validateLastNameUseCase.execute(data.last_name).collect()
        validateEmailUseCase.execute(data.email).collect()
        validateUserNameUseCase.execute(data.username).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        validatePasswordUseCase.execute(data.password).collectLatest {
            async {
                assertEquals(true,viewModel.validFirstName.value)
                assertEquals(true, viewModel.validLastName.value)
                assertEquals(true, viewModel.validEmail.value)
                assertEquals(true, viewModel.validUsername.value)
                assertEquals(false, viewModel.validPassword.value)
            }.join()
        }
    }




    @Test
    fun `successful signup return true`() = runTest {
        // given
        val data = SignupModel(first_name = "Tanvir", last_name = "Ahmed", email = "atanvir266@gmail.com",
                               username = "atanvir266", password = "12345", profile_icon = "")

        // when
        viewModel.makeSignup(data)
        validateFirstNameUseCase.execute(data.first_name).collect()
        validateLastNameUseCase.execute(data.last_name).collect()
        validateEmailUseCase.execute(data.email).collect()
        validateUserNameUseCase.execute(data.username).collect()
        validatePasswordUseCase.execute(data.password).collect()
        dispatcher.scheduler.advanceUntilIdle()


        // than
        makeSignUpUseCase.execute(data).collectLatest {
            async {

                assertEquals(true,viewModel.validFirstName.value)
                assertEquals(true, viewModel.validLastName.value)
                assertEquals(true, viewModel.validEmail.value)
                assertEquals(true, viewModel.validUsername.value)
                assertEquals(true, viewModel.validPassword.value)

                assertEquals(false, viewModel.signup.value is NetworkResult.Loading)
                delay(2000)
                assertEquals(true,viewModel.signup.value is NetworkResult.Success)
            }.join()
        }
    }

}