package com.example.yousave.databaseClasses

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun toDate(value:Long): Date{
        return Date(value)
    }

    @TypeConverter
    fun toLong(value: Date): Long{
        return value.time
    }
}