package com.tete.takenote.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {
    /*@Provides
    @ActivityScoped
    fun provideNavigator(activity: Activity): Navigator =
        NavigatorImpl(activity)*/
}