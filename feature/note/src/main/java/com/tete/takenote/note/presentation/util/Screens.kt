package com.tete.takenote.note.presentation.util

sealed class Screens(val route: String) {
    object NotesScreen: Screens(Routes.NOTES)
    object AddEditNoteScreen: Screens(Routes.ADD_EDIT_NOTE)
}
