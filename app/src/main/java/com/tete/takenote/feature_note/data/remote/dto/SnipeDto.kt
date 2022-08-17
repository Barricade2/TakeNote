package com.tete.takenote.feature_note.data.remote.dto

import com.tete.takenote.feature_note.domain.model.Snipe

data class SnipeDto(
    val text: String
) {
    fun toSnipe(): Snipe {
        return Snipe (
            text = text
        )
    }
}