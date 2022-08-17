package com.tete.takenote.feature_note.presentation.notes

import com.tete.takenote.feature_note.data.local.entity.NoteEntity
import com.tete.takenote.feature_note.domain.util.NoteOrder
import com.tete.takenote.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<NoteEntity> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
)
