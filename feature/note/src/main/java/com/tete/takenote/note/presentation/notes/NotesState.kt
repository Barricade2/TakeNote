package com.tete.takenote.note.presentation.notes

import com.tete.takenote.note.data.local.entity.NoteEntity
import com.tete.takenote.note.domain.util.NoteOrder
import com.tete.takenote.note.domain.util.OrderType

data class NotesState(
    val notes: List<NoteEntity> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
)
