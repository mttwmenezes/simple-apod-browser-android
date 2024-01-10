package br.com.mathsemilio.simpleapodbrowser.di

import br.com.mathsemilio.simpleapodbrowser.BuildConfig
import br.com.mathsemilio.simpleapodbrowser.infrastructure.service.ApodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApodService(retrofit: Retrofit): ApodService {
        return retrofit.create(ApodService::class.java)
    }
}
