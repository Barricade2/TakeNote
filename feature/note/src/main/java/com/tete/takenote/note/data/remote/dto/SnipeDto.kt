package com.tete.takenote.note.data.remote.dto

import com.tete.takenote.note.domain.model.Snipe

data class SnipeDto(
    val text: String
) {
    fun toSnipe(): Snipe {
        return Snipe (
            text = text
        )
    }
}