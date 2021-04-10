package com.tutorial.basicmvvm.db.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    companion object {
        @TypeConverter
        fun toDate(timestamp: Long?): Date? = if (timestamp == null) null else Date(timestamp)


        @TypeConverter
        fun toTimestamp(date: Date?): Long? = date?.time
    }
}