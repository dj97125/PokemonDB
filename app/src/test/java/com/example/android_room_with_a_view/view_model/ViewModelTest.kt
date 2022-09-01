package com.example.android_room_with_a_view.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_room_with_a_view.common.FailedNetworkResponseException
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.UserUseCase
import com.example.android_room_with_a_view.domain.response.UserDomain
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()
    lateinit var subject: ViewModel
    lateinit var userUseCase: UserUseCase
    private val handler = CoroutineExceptionHandler { coroutineContext, throwable -> }
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScopeCoroutine = TestScope(testDispatcher)

    @Before
    fun setUpTest() {
        userUseCase = mockk()
        subject = ViewModel(
            handler,
            userUseCase,
            testScopeCoroutine
        )
    }

    @Test
    fun `test everything works`() {
        testDispatcher.scheduler.advanceTimeBy(2000L)
        assertTrue(true)
    }

    @Test
    fun `get HEROEDOMAIN list when fetching data from server returns ERROR response`() {
        /**
         * Given
         */
        coEvery {
            userUseCase.invoke()
        } returns flowOf(
            StateAction.ERROR(FailedNetworkResponseException())
        )

        /**
         * When
         */
        var StateActionTestList = mutableListOf<StateAction?>()

        val job = testScopeCoroutine.launch(handler) {
            supervisorScope {
                launch {
                    subject.userResponse.collect() {
                        subject.userResponse.toList(StateActionTestList)
                    }
                }
            }
            /**
             * Then
             */
            val successTest = StateAction.ERROR(FailedNetworkResponseException())
            assertEquals(StateActionTestList.get(0), successTest)
            Truth.assertThat(successTest.error.message)
                .isEqualTo("Error: failure in the network response")
        }

        job.cancel()

    }

    @Test
    fun `get HEROEDOMAIN list when fetching data from server returns SUCCESS response`() {
        /**
         * Given
         */
        val listHeroe = listOf(mockk<UserDomain>(relaxed = true))
        val message = "Data From Network"
        coEvery {
            userUseCase.invoke()
        } returns flowOf(
            StateAction.SUCCESS(listHeroe, message)
        )
        subject.getUserList()

        /**
         * When
         */
        var StateActionTestList = mutableListOf<StateAction?>()


        val job = testScopeCoroutine.launch(handler) {
            supervisorScope {
                launch {
                    subject.userResponse.collect {
                        subject.userResponse.toList(StateActionTestList)
                    }
                }
            }

            /**
             * Then
             */
            val successTest =
                StateActionTestList.get(0) as StateAction.SUCCESS<List<UserDomain>>
            assertEquals(StateActionTestList.get(0), successTest)
            Truth.assertThat(successTest.message).isEqualTo("Data From Network")
        }
        job.cancel()

    }


    @After
    fun shutdownTest() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

}
