package com.tete.takenote.note.data.local.datastore

import androidx.datastore.preferences.core.Preferences

interface Datastore {
    suspend fun  <T> saveValue(key: Preferences.Key<T>, value: T)
    suspend fun <T> getValue(key: Preferences.Key<T>, responseFunc: T.() -> Unit)
}