package com.example.recipeapp.common

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type

class DateTimeConverters : JsonSerializer<DateTime>, JsonDeserializer<DateTime?> {

    override fun serialize(
        src: DateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val dateTimeNoMillisFmt = ISODateTimeFormat.dateTimeNoMillis()
        return JsonPrimitive(dateTimeNoMillisFmt.print(src))
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): DateTime? {
        return convertStringToDate(json.asString)
    }

    companion object {
        private val DATE_FORMATS = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd"
        )

        fun convertStringToDate(value: String?): DateTime? {
            for (format in DATE_FORMATS) {

                try {
                    return if (value?.toIntOrNull() != null) {
                        DateTime(value.toLong() * 1000, DateTimeZone.getDefault())
                    } else {
                        val formatter = DateTimeFormat.forPattern(format)
                        formatter.parseDateTime(value)
                    }
                } catch (e: IllegalArgumentException) {
                    continue
                }
            }
            return null
        }
    }
}