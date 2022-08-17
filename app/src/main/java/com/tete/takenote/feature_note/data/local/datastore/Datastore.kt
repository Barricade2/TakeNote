package com.tete.takenote.feature_note.data.local.datastore

import androidx.datastore.preferences.core.Preferences

interface Datastore {
    suspend fun  <T> saveValue(key: Preferences.Key<T>, value: T)
    suspend fun <T> getValue(key: Preferences.Key<T>, responseFunc: T.() -> Unit)
}