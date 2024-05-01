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

package br.com.mathsemilio.simpleapodbrowser

import android.app.Application
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import br.com.mathsemilio.simpleapodbrowser.common.di.CompositionRoot
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleApodBrowserApplication : Application() {

    private lateinit var compositionRoot: CompositionRoot

    override fun onCreate() {
        super.onCreate()

        System.setProperty(IO_PARALLELISM_PROPERTY_NAME, Int.MAX_VALUE.toString())

        compositionRoot = CompositionRoot(this)
    }

    fun getCompositionRoot(): CompositionRoot {
        return compositionRoot
    }
}
