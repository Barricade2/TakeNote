package com.tete.takenote.note.domain.use_case

import com.tete.takenote.note.data.local.entity.NoteEntity
import com.tete.takenote.note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): NoteEntity? {
        return repository.getNoteById(id)
    }
}