package com.example.organizer.data

class UserRepository(private val dao: UserDao) {

    fun getAllUsers(): List<User> = dao.getAll()

    suspend fun upsertUser(user: User) {
        dao.upsertUser(user)
    }
}