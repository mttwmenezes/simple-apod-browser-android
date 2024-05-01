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

import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

data class LatestApodsUiState(
    val loading: Boolean,
    val apods: List<Apod>,
    val failure: Boolean
) {
    companion object {
        val loading = LatestApodsUiState(loading = true, apods = emptyList(), failure = false)
        val failure = LatestApodsUiState(loading = false, apods = emptyList(), failure = true)
    }
}
