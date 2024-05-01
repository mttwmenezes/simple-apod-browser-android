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

package br.com.mathsemilio.simpleapodbrowser.data.repository

import br.com.mathsemilio.simpleapodbrowser.data.source.ApodSource
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.infrastructure.network.response.ApodResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface ApodFetching {
    suspend fun fetchLatest(): List<Apod>
}

class ApodRepository @Inject constructor(
    private val source: ApodSource
) : ApodFetching {

    override suspend fun fetchLatest(): List<Apod> {
        val startOfWeek = LocalDate.now()
            .minusWeeks(1)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        return source.fetchStartingFrom(startOfWeek).map { toApodFrom(it) }.reversed()
    }

    private fun toApodFrom(response: ApodResponse) = Apod(
        title = response.title,
        url = response.url,
        date = response.date,
        mediaType = response.mediaType,
        explanation = response.explanation,
        thumbnailUrl = response.thumbnailUrl,
        copyright = response.copyright
    )
}
