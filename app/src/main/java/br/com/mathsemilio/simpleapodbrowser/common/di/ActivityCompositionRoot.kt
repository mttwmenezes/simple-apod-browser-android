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

package br.com.mathsemilio.simpleapodbrowser.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.*
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.database.FavoriteApodDatabase
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.ui.common.permission.PermissionHandler

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    val permissionHandler by lazy {
        PermissionHandler(activity)
    }

    val application
        get() = compositionRoot.application

    val containerLayoutDelegate
        get() = activity as ContainerLayoutDelegate

    val eventPublisher
        get() = compositionRoot.eventPublisher

    val eventSubscriber
        get() = compositionRoot.eventSubscriber

    val fragmentManager
        get() = activity.supportFragmentManager

    val apodEndpoint
        get() = ApodEndpoint(compositionRoot.apodService)

    val favoriteApodEndpoint
        get() = FavoriteApodEndpoint(FavoriteApodDatabase.getDatabase(application).favoriteApodDao)
}
