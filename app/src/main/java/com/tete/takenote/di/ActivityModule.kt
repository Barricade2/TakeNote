package com.tete.takenote.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
@Module(
    includes = [
        NavigationModule::class
    ]
)
@InstallIn(ActivityComponent::class)
interface ActivityModule {
}