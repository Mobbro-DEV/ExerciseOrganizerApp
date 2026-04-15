package com.example.organizer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val firstName: String?,
    val lastName: String?,
)