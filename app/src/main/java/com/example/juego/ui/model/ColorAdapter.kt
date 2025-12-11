package com.example.juego.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ColorAdapter : TypeAdapter<Color>() {
    override fun write(out: JsonWriter, value: Color?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(String.format("#%08X", value.toArgb()))
    }

    override fun read(`in`: JsonReader): Color? {
        if (`in`.peek() == com.google.gson.stream.JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        val colorString = `in`.nextString()
        return if (colorString != null) {
            Color(android.graphics.Color.parseColor(colorString))
        } else {
            null
        }
    }
}
