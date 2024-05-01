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

import retrofit2.Retrofit
import android.app.Application
import br.com.mathsemilio.simpleapodbrowser.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import br.com.mathsemilio.simpleapodbrowser.infrastructure.network.service.ApodService
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventBus
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber

class CompositionRoot(val application: Application) {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val eventBus by lazy {
        EventBus()
    }

    val apodService: ApodService by lazy {
        retrofit.create(ApodService::class.java)
    }

    val eventPublisher by lazy {
        EventPublisher(eventBus)
    }

    val eventSubscriber by lazy {
        EventSubscriber(eventBus)
    }
}
