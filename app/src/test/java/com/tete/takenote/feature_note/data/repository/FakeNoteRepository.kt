package com.tete.takenote.feature_note.data.repository

import com.tete.takenote.feature_note.data.local.entity.NoteEntity
import com.tete.takenote.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<NoteEntity>()

    override fun getNotes(): Flow<List<NoteEntity>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Int): NoteEntity? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: NoteEntity) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        notes.remove(note)
    }
}