package com.tete.takenote.note.data.remote.dto

import com.tete.takenote.note.data.local.entity.NoteEntity

data class NoteDto(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val snipe: List<SnipeDto>?
    //val UUID: Int
    ) {
    fun toWordInfoEntity(): NoteEntity {
        return NoteEntity (
            snipe = snipe?.map { it.toSnipe() },
            title = title,
            content = content,
            timestamp = timestamp,
            color = color
            //UUID = UUID
        )
    }
}