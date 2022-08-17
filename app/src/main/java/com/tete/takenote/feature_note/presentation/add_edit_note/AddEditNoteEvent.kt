package com.tete.takenote.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class OnTitleValueChange(val value: String): AddEditNoteEvent()
    data class OnTitleFocusChange(val focusState: FocusState): AddEditNoteEvent()
    data class OnContentValueChange(val value: String): AddEditNoteEvent()
    data class OnContentFocusChange(val focusState: FocusState): AddEditNoteEvent()
    data class OnColorClick(val color: Int): AddEditNoteEvent()
    object OnSaveNoteClick: AddEditNoteEvent()
}

