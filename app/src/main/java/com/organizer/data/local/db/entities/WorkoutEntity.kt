package com.organizer.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("workout")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Long = 0L,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
