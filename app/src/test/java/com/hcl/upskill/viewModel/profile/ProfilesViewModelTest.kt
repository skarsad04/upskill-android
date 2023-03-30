@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hcl.upskill.viewModel.profile

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hcl.upskill.base.BaseDispatcher
import com.hcl.upskill.domain.profile.list.ProfilesUseCase
import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.ui.profile.listing.ProfilesRepository
import com.hcl.upskill.ui.profile.listing.ProfilesViewModel
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ProfilesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    // ViewModel
    private lateinit var viewModel : ProfilesViewModel

    // Domain
    lateinit var profileUseCase : ProfilesUseCase

    // Up skill API
    @Mock lateinit var apiSkill: Skill


    // Dispatcher
    private val baseDispatcher = BaseDispatcher()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)

        profileUseCase = ProfilesUseCase(ProfilesRepository(apiSkill))
        viewModel =  ProfilesViewModel(dispatcher = baseDispatcher,
                                        profilesUseCase = profileUseCase)
    }

    @Test
    fun `profile list with token return true`() = runTest {
        // given
        val token = "access_token"

        // when
        viewModel.profile(token)

       // than
       profileUseCase.execute(token).collectLatest {
           async {
               assertEquals(false,viewModel.profile.value is NetworkResult.Loading)
               delay(1000)
               assertEquals(true,viewModel.profile.value is NetworkResult.Success)
           }.join()
       }
    }


    @Test
    fun `profile list without token return true`() = runTest {
        // given
        val token = ""

        // when
        viewModel.profile(token)

        // than
        profileUseCase.execute(token).collectLatest {
            async {
                assertEquals(false, viewModel.profile.value is NetworkResult.Loading)
                delay(1000)
                assertEquals(true, viewModel.profile.value is NetworkResult.Success)
            }.join()
        }
    }


}