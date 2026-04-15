package com.example.organizer.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.organizer.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
}