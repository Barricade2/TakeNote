package com.tete.takenote.feature_note.presentation.util

object Routes {
    const val NOTES = "notes_screen"
    const val ADD_EDIT_NOTE = "add_edit_note_screen?noteId={noteId}&noteColor={noteColor}"
}

fun String.replace(vararg pairs: Pair<String, Any?>): String =
    pairs.fold(this) { acc, (old, new) -> acc.replace(old, new.toString(), ignoreCase = true) }