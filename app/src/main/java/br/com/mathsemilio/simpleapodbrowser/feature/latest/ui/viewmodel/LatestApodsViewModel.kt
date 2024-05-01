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
import br.com.mathsemilio.simpleapodbrowser.data.repository.ApodFetching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestApodsViewModel @Inject constructor(
    private val repository: ApodFetching
) : ViewModel() {

    private val _uiState : MutableLiveData<LatestApodsUiState> = MutableLiveData()
    val uiState: LiveData<LatestApodsUiState>
        get() = _uiState

    init {
        fetchLatest()
    }

    fun fetchLatest() {
        _uiState.value = LatestApodsUiState.loading
        viewModelScope.launch {
            repository.fetchLatest().also {
                val state = if (it.isNotEmpty()) {
                    LatestApodsUiState(loading = false, apods = it, failure = false)
                } else {
                    LatestApodsUiState.failure
                }
                _uiState.postValue(state)
            }
        }
    }
}
