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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mathsemilio.simpleapodbrowser.feature.latest.data.repository.LatestApodsRepository
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.model.LatestUiState
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list.PeriodFilterOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LatestApodsViewModel @Inject constructor(
    private val repository: LatestApodsRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<LatestUiState> = MutableLiveData()
    private val uiState: LiveData<LatestUiState>
        get() = _uiState

    init {
        viewModelScope.launch { retrieveFrom(PeriodFilterOption.LAST_WEEK) }
    }

    fun retrieveFrom(periodSelected: PeriodFilterOption) {
        val loadingState = LatestUiState(periodSelected, loading = true, displayError = false)
        val errorState = LatestUiState(periodSelected, loading = false, displayError = true)

        _uiState.postValue(loadingState)

        viewModelScope.launch {
            repository.retrieveFromDateRange(formattedStartDate(periodSelected)).also {
                if (it.isNotEmpty()) {
                    val successState = LatestUiState(
                        periodSelected,
                        loading = false,
                        displayError = false,
                        apods = it
                    )
                    _uiState.postValue(successState)
                } else {
                    _uiState.postValue(errorState)
                }
            }
        }
    }

    private fun formattedStartDate(periodSelected: PeriodFilterOption): String {
        val startDate = when (periodSelected) {
            PeriodFilterOption.LAST_WEEK -> LocalDate.now().minusWeeks(1)
            PeriodFilterOption.LAST_TWO_WEEKS -> LocalDate.now().minusWeeks(2)
            PeriodFilterOption.LAST_MONTH -> LocalDate.now().minusMonths(1)
        }

        return startDate.format(DateTimeFormatter.ofPattern("yyyy-mm-dd"))
    }
}
