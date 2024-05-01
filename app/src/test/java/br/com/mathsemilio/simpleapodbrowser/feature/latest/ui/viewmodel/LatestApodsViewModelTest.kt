/*
Copyright 2024 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.simpleapodbrowser.data.repository.ApodFetching
import br.com.mathsemilio.simpleapodbrowser.rule.MainDispatcherRule
import br.com.mathsemilio.simpleapodbrowser.util.getOrAwaitValue
import br.com.mathsemilio.simpleapodbrowser.util.latestApods
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LatestApodsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: LatestApodsViewModel

    @Mock
    private lateinit var apodRepositoryMock: ApodFetching

    @Before
    fun setup() {
        arrangeSuccess()
        sut = LatestApodsViewModel(apodRepositoryMock)
    }

    private fun arrangeSuccess() = runTest {
        `when`(apodRepositoryMock.fetchLatest()).thenReturn(latestApods.reversed())
    }

    @Test
    fun testFetchLatest_failure_failureStatePublished() {
        arrangeFailure()
        sut.fetchLatest()
        assertTrue(sut.uiState.getOrAwaitValue() == LatestApodsUiState.failure)
    }

    private fun arrangeFailure() = runTest {
        `when`(apodRepositoryMock.fetchLatest()).thenReturn(emptyList())
    }

    @Test
    fun testFetchLatest_success_successStatePublished() {
        arrangeSuccess()
        sut.fetchLatest()
        assertTrue(sut.uiState.getOrAwaitValue().apods.isNotEmpty())
        assertEquals(latestApods.reversed(), sut.uiState.getOrAwaitValue().apods)
        assertFalse(sut.uiState.getOrAwaitValue().loading)
        assertFalse(sut.uiState.getOrAwaitValue().failure)
    }
}
