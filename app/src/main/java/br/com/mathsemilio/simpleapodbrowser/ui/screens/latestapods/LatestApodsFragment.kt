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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.latestapods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodFromDateUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodFromDateUseCase.FetchApodFromDateResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase.FetchRandomApodResult
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.viewmodel.LatestApodsUiState
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.viewmodel.LatestApodsViewModel
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.screens.latestapods.view.LatestApodsScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.latestapods.view.LatestApodsScreenViewImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LatestApodsFragment : BaseFragment(), LatestApodsScreenView.Listener, EventListener {

    private lateinit var view: LatestApodsScreenView

    private val viewModel: LatestApodsViewModel by viewModels()

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var fetchRandomApodUseCase: FetchRandomApodUseCase
    private lateinit var fetchApodBasedOnDateUseCase: FetchApodFromDateUseCase

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        screensNavigator = ScreensNavigator(findNavController())
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager

        eventSubscriber = compositionRoot.eventSubscriber

        fetchRandomApodUseCase = compositionRoot.fetchRandomApodUseCase
        fetchApodBasedOnDateUseCase = compositionRoot.fetchApodFromDateUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = LatestApodsScreenViewImpl(inflater, container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when {
               state.loading -> onLoading()
               state.success() -> onSuccess(state.apods)
               state.failure -> onFailure()
            }
        }
    }

    private fun onLoading() {
        view.showProgressIndicator()
        view.hideNetworkRequestErrorState()
    }

    private fun LatestApodsUiState.success() = apods.isNotEmpty()

    private fun onSuccess(apods: List<Apod>) {
        view.hideProgressIndicator()
        view.hideNetworkRequestErrorState()
        view.bind(apods)
    }

    private fun onFailure() {
        view.hideProgressIndicator()
        view.showNetworkRequestErrorState()
    }

    override fun onApodClicked(apod: Apod) {
        screensNavigator.toApodDetailsScreen(apod)
    }

    override fun onScreenSwipedToRefresh() {
        viewModel.fetchLatest()
    }

    override fun onEvent(event: Any) {
        if (event is DateSetEvent.DateSet)
            fetchApodBasedOn(event.dateInMillis)
    }

    private fun fetchApodBasedOn(dateSetMillis: Long) {
        coroutineScope.launch {
            handleFetchApodBasedOnDateResult(fetchApodBasedOnDateUseCase.fetchFrom(dateSetMillis))
        }
    }

    private fun handleFetchApodBasedOnDateResult(result: FetchApodFromDateResult) {
        when (result) {
            is FetchApodFromDateResult.Completed -> onFetchApodFromDateCompleted(result.apod)
            FetchApodFromDateResult.Failed -> messagesManager.showCheckYourConnectionMessage()
        }
    }

    private fun onFetchApodFromDateCompleted(apod: Apod?) {
        apod?.let {
            screensNavigator.toApodDetailsScreen(it)
        } ?: run {
            messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_latest_apods, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_pick_date -> {
                dialogManager.showDatePickerDialog()
                true
            }
            R.id.toolbar_action_get_random_apod -> {
                fetchRandomApod()
                true
            }
            R.id.toolbar_action_settings -> {
                screensNavigator.toSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchRandomApod() {
        coroutineScope.launch {
            handleFetchRandomApodUseCaseResult(fetchRandomApodUseCase.fetchRandomApod())
        }
    }

    private fun handleFetchRandomApodUseCaseResult(result: FetchRandomApodResult) {
        when (result) {
            is FetchRandomApodResult.Completed -> onFetchRandomApodCompleted(result.randomApod)
            FetchRandomApodResult.Failed -> messagesManager.showCheckYourConnectionMessage()
        }
    }

    private fun onFetchRandomApodCompleted(randomApod: Apod?) {
        randomApod?.let {
            screensNavigator.toApodDetailsScreen(it)
        } ?: run {
            messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    override fun onStart() {
        super.onStart()
        view.addObserver(this)
        eventSubscriber.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeObserver(this)
        eventSubscriber.unsubscribe(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
