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

package br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list

sealed class LatestListItem {

    data object SmallSpacer : LatestListItem()

    data object RegularSpacer : LatestListItem()

    data class PeriodFilter(
        val periodSelected: PeriodFilterOption = PeriodFilterOption.LAST_WEEK
    ) : LatestListItem()

    data class Prominent(
        val id: String,
        val imageUrl: String?,
        val title: String,
        val mediaType: String
    ) : LatestListItem()

    data class Regular(
        val id: String,
        val imageUrl: String?,
        val title: String,
        val mediaType: String,
        val formattedDate: String
    ) : LatestListItem()
}
