package com.tete.takenote.feature_note.domain.repository

import com.tete.takenote.feature_note.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<NoteEntity>>

    suspend fun getNoteById(id: Int): NoteEntity?

    suspend fun insertNote(note: NoteEntity)

    suspend fun deleteNote(note: NoteEntity)
}