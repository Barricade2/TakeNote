package com.tete.takenote.note.data.local.converter

import java.lang.reflect.Type

interface JsonParserI {

    fun <T> fromJson(json: String, type: Type): T?

    fun <T> toJson(obj: T, type: Type): String?
}