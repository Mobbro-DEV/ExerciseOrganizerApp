package com.example.organizer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Upsert
    suspend fun upsertUser(user: User)

//    @Delete
//    fun delete(user: User)
//
//    @Query("""
//        SELECT * FROM user
//        WHERE first_name LIKE :first AND last_name LIKE :last
//        LIMIT 1
//    """)
//    suspend fun findByName(first: String, last: String): User?
}