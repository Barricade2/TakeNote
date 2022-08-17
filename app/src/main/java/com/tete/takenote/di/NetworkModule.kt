package com.tete.takenote.di

import com.tete.takenote.feature_note.data.remote.RestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        NetworkingModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesBaseUrl(): String {
        return "https://api.takenote.dev/"
    }

    @Provides
    @Singleton
    fun provideRestApiService(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }
}