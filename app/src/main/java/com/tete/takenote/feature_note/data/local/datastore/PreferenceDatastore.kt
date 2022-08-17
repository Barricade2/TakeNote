package com.tete.takenote.feature_note.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.tete.takenote.feature_note.data.local.datastore.PreferenceDatastore.Companion.PREFERENCES_STORE
import com.tete.takenote.feature_note.data.local.datastore.PreferencesKeys.INT_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_STORE)


class PreferenceDatastore @Inject constructor(@ApplicationContext appContext: Context): Datastore {

    companion object {
        const val PREFERENCES_STORE = "preferences_store"
        const val PREFERENCES_SETTINGS = "preferences_settings"
    }

    private val dataStore = appContext.dataStore

    val getIntValue: Flow<Int> = dataStore.data.map { preferences ->
        preferences[INT_KEY] ?: 0
    }

    suspend fun saveIntValue(value: Int) {
        try {
            dataStore.edit { settings ->
                settings[INT_KEY] = value
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        try {
            dataStore.edit {
                it[key] = value
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override suspend fun <T> getValue(key: Preferences.Key<T>, responseFunc: T.() -> Unit) {
        dataStore.get(key) {
            responseFunc.invoke(this)
        }
    }
}

private suspend fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>, func: T.() -> Unit) {
    data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else { throw it } }

        .map {preferences ->
            preferences[key]
        }.collect {
            it?.let { func.invoke(it as T) } }
}