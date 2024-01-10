package br.com.mathsemilio.simpleapodbrowser.feature.latest.data.source

import br.com.mathsemilio.simpleapodbrowser.infrastructure.networking.response.ApodResponse
import br.com.mathsemilio.simpleapodbrowser.infrastructure.service.ApodService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class LatestApodsSource @Inject constructor(
    private val service: ApodService
) {
    suspend fun retrieveFromDateRange(startDate: String): List<ApodResponse> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext service.retrieveFromDateRange(
                    startDate = startDate
                ).body().orEmpty()
            } catch (e: Exception) {
                return@withContext emptyList()
            }
        }
    }
}