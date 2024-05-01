/*
Copyright 2021 Matheus Menezes

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

package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.domain.model.*
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint

class FetchFavoriteApodsUseCase(private val endpoint: FavoriteApodEndpoint) {

    sealed class FetchFavoriteApodsResult {
        data class Completed(val favoriteApods: List<Apod>) : FetchFavoriteApodsResult()

        object Failed : FetchFavoriteApodsResult()
    }

    suspend fun fetchFavoriteApods(): FetchFavoriteApodsResult {
        var result: FetchFavoriteApodsResult

        endpoint.fetchFavoriteApods().also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed ->
                    FetchFavoriteApodsResult.Completed(endpointResult.data ?: emptyList())
                is Result.Failed ->
                    FetchFavoriteApodsResult.Failed
            }
        }

        return result
    }
}
