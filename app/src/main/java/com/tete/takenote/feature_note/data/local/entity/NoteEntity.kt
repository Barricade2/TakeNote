package com.tete.takenote.feature_note.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tete.takenote.feature_note.data.local.converter.Converters
import com.tete.takenote.feature_note.domain.model.Note
import com.tete.takenote.feature_note.domain.model.Snipe
import com.tete.takenote.ui.theme.*

@Entity
data class NoteEntity(
    @PrimaryKey
    val id: Int? = null,
    //val UUID: Int = UUID() UUID.randomUUID().toString()
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @TypeConverters(Converters::class)
    val snipe: List<Snipe>? = null
) {
    fun toNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            timestamp = timestamp,
            color = color,
            snipe = snipe
        )
    }
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)

        fun createNoteEntity(note: Note): NoteEntity {
            return NoteEntity(
                id = note.id,
                title = note.title,
                content = note.content,
                timestamp = note.timestamp,
                color = note.color,
                snipe = note.snipe
            )
        }
    }
}

class InvalidNoteException(message: String): Exception(message)