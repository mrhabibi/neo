package com.mrhabibi.neo.model

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun toMap(value: String): Map<String, String>? {
        val mapType = object : TypeToken<Map<String, String>>() {
        }.getType()
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun toJsonElement(value: String): JsonElement? {
        return JsonParser().parse(value)
    }

    @TypeConverter
    fun fromJsonElement(element: JsonElement): String {
        return Gson().toJson(element)
    }
}