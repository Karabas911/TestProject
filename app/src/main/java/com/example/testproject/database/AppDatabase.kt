package com.example.testproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testproject.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}