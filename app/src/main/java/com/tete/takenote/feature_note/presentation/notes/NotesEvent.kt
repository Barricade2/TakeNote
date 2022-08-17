package com.tete.takenote.feature_note.presentation.notes

import com.tete.takenote.feature_note.data.local.entity.NoteEntity
import com.tete.takenote.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class OnNoteClick(val note: NoteEntity): NotesEvent()
    object OnAddNoteClick: NotesEvent()
    data class OnOrderChange(val noteOrder: NoteOrder): NotesEvent()
    data class OnDeleteNoteClick(val note: NoteEntity): NotesEvent()
    object OnUndoDeleteNoteClick: NotesEvent()
    object OnToggleOrderSectionClick: NotesEvent()
}
