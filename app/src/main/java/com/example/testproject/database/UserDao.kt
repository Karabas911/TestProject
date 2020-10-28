package com.example.testproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.testproject.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?

    @Insert
    suspend fun insert(user: User): Long

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun findById(userId: Int): User

}