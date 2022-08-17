package com.tete.takenote.di

import android.content.Context
import android.content.res.Resources
import com.tete.takenote.MainApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(
    includes = [
        DatabaseModule::class,
        NetworkModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Provides
    @Singleton
    fun resources(application: MainApplication): Resources = application.resources
}