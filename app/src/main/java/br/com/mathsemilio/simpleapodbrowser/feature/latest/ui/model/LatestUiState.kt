package br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.model

import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list.PeriodFilterOption
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list.PeriodFilterOption.LAST_WEEK

data class LatestUiState(
    val periodSelected: PeriodFilterOption = LAST_WEEK,
    val loading: Boolean = true,
    val displayError: Boolean = false,
    val apods: List<Apod> = emptyList()
)
