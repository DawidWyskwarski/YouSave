package com.example.yousave.databaseClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val date: Date,
    val category:String,
    val money:Double,
    val title:String,
    val description:String
)
