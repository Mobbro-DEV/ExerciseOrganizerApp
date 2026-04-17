package com.example.organizer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Exercise(
    @PrimaryKey var eId: String = UUID.randomUUID().toString(),
    var name: String,
    var imageRes: Int,
    var category: String,
)
