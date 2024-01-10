package br.com.mathsemilio.simpleapodbrowser.feature.latest.data.repository

import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.feature.latest.data.source.LatestApodsSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LatestApodsRepository @Inject constructor(
    private val source: LatestApodsSource
) {
    suspend fun retrieveFromDateRange(startDate: String): List<Apod> {
        return source.retrieveFromDateRange(startDate).map {
            Apod(
                title = it.title.orEmpty(),
                url = it.url,
                date = it.date.orEmpty(),
                mediaType = it.mediaType.orEmpty(),
                explanation = it.explanation.orEmpty(),
                thumbnailUrl = it.thumbnailUrl,
                copyright = it.copyright
            )
        }
    }
}
