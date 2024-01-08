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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.os.Bundle
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddApodToFavoritesUseCase.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodStatusUseCase.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.image.ImageExporter
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.permission.Permission
import br.com.mathsemilio.simpleapodbrowser.ui.common.permission.PermissionHandler
import br.com.mathsemilio.simpleapodbrowser.ui.common.permission.PermissionRequestResult
import br.com.mathsemilio.simpleapodbrowser.ui.common.util.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.promptdialog.PromptDialogEvent
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.view.*
import kotlinx.coroutines.*

class ApodDetailFragment : BaseFragment(),
    ApodDetailView.Listener,
    PermissionHandler.RequestResultListener,
    ImageExporter.Listener,
    EventListener {

    private lateinit var view: ApodDetailView

    private lateinit var permissionHandler: PermissionHandler
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager
    private lateinit var imageExporter: ImageExporter

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var addFavoriteApodUseCase: AddApodToFavoritesUseCase
    private lateinit var deleteFavoriteApodUseCase: DeleteFavoriteApodUseCase
    private lateinit var fetchRandomApodUseCase: FetchRandomApodUseCase
    private lateinit var fetchFavoriteApodStatusUseCase: FetchFavoriteApodStatusUseCase

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private lateinit var toolbarActionAddApodToFavorites: MenuItem
    private lateinit var toolbarActionGetRandomApod: MenuItem
    private lateinit var toolbarActionDeleteFavoriteApod: MenuItem

    private lateinit var apod: Apod
    private var isRandomApod = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        permissionHandler = compositionRoot.permissionsHandler
        screensNavigator = ScreensNavigator(findNavController())
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager
        imageExporter = compositionRoot.imageExporter

        eventSubscriber = compositionRoot.eventSubscriber

        addFavoriteApodUseCase = compositionRoot.addFavoriteApodUseCase
        deleteFavoriteApodUseCase = compositionRoot.deleteFavoriteApodUseCase
        fetchRandomApodUseCase = compositionRoot.fetchRandomApodUseCase
        fetchFavoriteApodStatusUseCase = compositionRoot.fetchFavoriteApodStatusUseCase

        apod = requireArguments().getSerializable(ARG_APOD) as Apod
        isRandomApod = requireArguments().getBoolean(ARG_IS_RANDOM_APOD, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ApodDetailViewImpl(inflater, container)
        return view.rootView
    }

    override fun onPlayIconClicked(videoUrl: String) = launchWebPage(videoUrl)

    override fun onPermissionRequestResult(result: PermissionRequestResult) {
        when (result) {
            PermissionRequestResult.GRANTED ->
                imageExporter.export(view.apodImageView.drawable.toBitmap())
            PermissionRequestResult.DENIED ->
                dialogManager.showGrantExternalStoragePermissionDialog()
            PermissionRequestResult.DENIED_PERMANENTLY ->
                dialogManager.showManuallyGrantPermissionDialog()
        }
    }

    override fun onApodImageExportedSuccessfully() {
        messagesManager.showApodImageExportedMessage()
    }

    override fun onExportApodImageFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is PromptDialogEvent -> handlePromptDialogEvent(event)
        }
    }

    private fun handlePromptDialogEvent(event: PromptDialogEvent) {
        when (event) {
            PromptDialogEvent.PositiveButtonClicked -> deleteFavoriteApod()
            PromptDialogEvent.NegativeButtonClicked -> { /* no-op - No action required */ }
        }
    }

    private fun deleteFavoriteApod() {
        coroutineScope.launch {
            handleDeleteFavoriteApodResult(deleteFavoriteApodUseCase.delete(apod))
        }
    }

    private fun handleDeleteFavoriteApodResult(result: DeleteFavoriteApodResult) {
        when (result) {
            DeleteFavoriteApodResult.Completed -> screensNavigator.navigateUp()
            DeleteFavoriteApodResult.Failed -> messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        toolbarActionAddApodToFavorites = menu.findItem(R.id.toolbar_action_add_to_favorites)
        toolbarActionGetRandomApod = menu.findItem(R.id.toolbar_action_get_random_apod)
        toolbarActionDeleteFavoriteApod = menu.findItem(R.id.toolbar_action_delete_favorite_apod)

        toolbarActionGetRandomApod.isVisible = isRandomApod
        menu.findItem(R.id.toolbar_action_image_copyright).isVisible = !apod.copyright.isNullOrEmpty()

        setupToolbarActionsBasedOnApodType()

        fetchFavoriteApodStatus()
    }

    private fun setupToolbarActionsBasedOnApodType() {
        if (apod.isFavorite)
            showToolbarActionsForFavoriteApod()
        else
            showToolbarActionsForRegularApod()
    }

    private fun showToolbarActionsForFavoriteApod() {
        toolbarActionDeleteFavoriteApod.isVisible = true
        toolbarActionAddApodToFavorites.isVisible = false
    }

    private fun showToolbarActionsForRegularApod() {
        toolbarActionDeleteFavoriteApod.isVisible = false
        toolbarActionAddApodToFavorites.isVisible = true
    }

    private fun fetchFavoriteApodStatus() {
        coroutineScope.launch {
            handleFetchFavoriteApodStatusResult(
                fetchFavoriteApodStatusUseCase.isAlreadyFavorite(apod)
            )
        }
    }

    private fun handleFetchFavoriteApodStatusResult(result: FetchFavoriteApodStatusResult) {
        when (result) {
            is FetchFavoriteApodStatusResult.Completed ->
                setupAddToFavoritesActionIcon(result.isFavorite)
            FetchFavoriteApodStatusResult.Failed ->
                messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    private fun setupAddToFavoritesActionIcon(isFavorite: Boolean) {
        if (isFavorite)
            setupAddToFavoritesToolbarActionForFavoriteApod()
        else
            setupAddToFavoritesToolbarActionForNonFavoriteApod()
    }

    private fun setupAddToFavoritesToolbarActionForFavoriteApod() {
        toolbarActionAddApodToFavorites.setIcon(R.drawable.ic_favorite)
        toolbarActionAddApodToFavorites.isEnabled = false
    }

    private fun setupAddToFavoritesToolbarActionForNonFavoriteApod() {
        toolbarActionAddApodToFavorites.setIcon(R.drawable.ic_favorite_border)
        toolbarActionAddApodToFavorites.isEnabled = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_apod_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_get_random_apod -> {
                fetchRandomApod()
                true
            }
            R.id.toolbar_action_add_to_favorites -> {
                addApodToFavorites()
                true
            }
            R.id.toolbar_action_delete_favorite_apod -> {
                dialogManager.showConfirmFavoriteApodDeletionDialog()
                true
            }
            R.id.toolbar_action_image_copyright -> {
                dialogManager.showImageCopyrightDialog(apod.copyright.orEmpty())
                true
            }
            R.id.toolbar_action_export_image -> {
                checkForWriteToExternalStoragePermission()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkForWriteToExternalStoragePermission() {
        if (permissionHandler.hasPermission(Permission.WRITE_EXTERNAL_STORAGE)) {
            imageExporter.export(view.apodImageView.drawable.toBitmap())
        } else {
            permissionHandler.request(Permission.WRITE_EXTERNAL_STORAGE)
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
        if (randomApod != null) {
            apod = randomApod
            view.bind(apod)
            fetchFavoriteApodStatus()
        } else {
            messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    private fun addApodToFavorites() {
        coroutineScope.launch {
            handleAddApodToFavoritesResult(addFavoriteApodUseCase.addToFavorites(apod))
        }
    }

    private fun handleAddApodToFavoritesResult(result: AddApodToFavoritesResult) {
        when (result) {
            AddApodToFavoritesResult.Completed -> onAddApodToFavoritesCompleted()
            AddApodToFavoritesResult.Failed -> messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    private fun onAddApodToFavoritesCompleted() {
        setupAddToFavoritesToolbarActionForFavoriteApod()
        messagesManager.showApodAddedToFavoritesMessage()
    }

    override fun onStart() {
        super.onStart()
        view.addObserver(this)
        eventSubscriber.subscribe(this)
        permissionHandler.addObserver(this)
        imageExporter.addObserver(this)
        view.bind(apod)
    }

    override fun onStop() {
        super.onStop()
        view.removeObserver(this)
        eventSubscriber.unsubscribe(this)
        permissionHandler.removeObserver(this)
        imageExporter.removeObserver(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
