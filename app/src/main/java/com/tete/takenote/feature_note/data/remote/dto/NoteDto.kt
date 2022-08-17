package com.tete.takenote.feature_note.data.remote.dto

import androidx.room.PrimaryKey
import com.tete.takenote.feature_note.data.local.entity.NoteEntity

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