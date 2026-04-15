package com.example.organizer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.organizer.dao.UserDao
import com.example.organizer.entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}