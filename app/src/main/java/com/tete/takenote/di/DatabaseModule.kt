package com.tete.takenote.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.tete.takenote.feature_note.data.local.converter.Converters
import com.tete.takenote.feature_note.data.local.converter.GsonParserImp
import com.tete.takenote.feature_note.data.local.datasource.AppDatabase
import com.tete.takenote.feature_note.data.local.datastore.Datastore
import com.tete.takenote.feature_note.data.local.datastore.PreferenceDatastore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    //@Named("RoomDatabase")
    fun provideNoteDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            //.fallbackToDestructiveMigration()
            .addTypeConverter(Converters(GsonParserImp(Gson())))
            .build()
    }

    /*interface ApplicationBindings {
        @Binds
        fun bindPreferencesDatastore(
            impl: PreferenceDatastore
        ): Datastore
    }*/


/*    @Singleton
    @Provides
    //@Named("PreferencesDataStore")
    fun providePreferencesDatastore(@ApplicationContext appContext: Context, @IoDispatcher CoroutineScope: CoroutineScope): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, PREFERENCES_STORE)),
            scope = CoroutineScope,
            produceFile = { appContext.preferencesDataStoreFile(PREFERENCES_STORE) }
        )
    }

    @Singleton
    @Provides
    fun providesCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)*/
}