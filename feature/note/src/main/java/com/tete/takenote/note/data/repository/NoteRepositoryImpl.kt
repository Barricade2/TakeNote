package com.tete.takenote.note.data.repository

import com.tete.takenote.note.data.local.datasource.NoteDao
import com.tete.takenote.note.data.local.entity.NoteEntity
import com.tete.takenote.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<NoteEntity>> {
        /*val notes = dao.getNotes().map { list ->
            list.map {
                it.toNote()
            }
        }*/
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): NoteEntity? {
        //val note = dao.getNoteById(id)?.toNote()
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: NoteEntity) {
        //val note = NoteEntity.createNoteEntity(note as Note)
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        //val note = NoteEntity.createNoteEntity(note as Note)
        dao.deleteNote(note)
    }
}