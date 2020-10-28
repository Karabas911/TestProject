package com.example.testproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(

    @PrimaryKey val id: Int,

    val name: String,

    val image: String?,

    val author: String,

    val description: String,

    val price: Double,

    var favorite: Boolean = false
)