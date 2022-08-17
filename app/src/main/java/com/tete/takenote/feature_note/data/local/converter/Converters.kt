package com.tete.takenote.feature_note.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.tete.takenote.feature_note.domain.model.Snipe

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParserI
) {
    @TypeConverter
    fun fromListSnipeJson(json: String): List<Snipe> {
        return jsonParser.fromJson<ArrayList<Snipe>>(
            json,
            object : TypeToken<ArrayList<Snipe>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toListSnipeJson(snipe: List<Snipe>?): String {
        return jsonParser.toJson(
            snipe,
            object : TypeToken<ArrayList<Snipe>>(){}.type
        ) ?: "[]"
    }
}