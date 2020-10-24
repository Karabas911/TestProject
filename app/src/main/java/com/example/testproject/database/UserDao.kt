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
    fun findByEmail(email: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

}