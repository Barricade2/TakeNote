package com.tete.takenote.feature_note.domain.use_case

import com.tete.takenote.feature_note.data.local.entity.InvalidNoteException
import com.tete.takenote.feature_note.data.local.entity.NoteEntity
import com.tete.takenote.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NoteEntity) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}