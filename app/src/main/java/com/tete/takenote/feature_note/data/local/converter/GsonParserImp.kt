package com.tete.takenote.feature_note.data.local.converter

import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParserImp(
    private val gson: Gson
): JsonParserI {

    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}