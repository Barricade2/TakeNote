package com.tete.takenote.note.domain.use_case

import com.tete.takenote.note.data.local.entity.NoteEntity
import com.tete.takenote.note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: NoteEntity) {
        repository.deleteNote(note)
    }
}