package com.example.testproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "email") val email: String,

    @ColumnInfo(name = "phone") val phone: String,

    @ColumnInfo(name = "password") val password: String
)