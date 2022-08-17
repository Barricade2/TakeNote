package com.tete.takenote.note.domain.model

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val snipe: List<Snipe>?
)
