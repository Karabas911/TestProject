package com.example.testproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(

    @PrimaryKey(autoGenerate = true) val id: Int,

    val name: String,

    val author: String,

    val description: String,

    val price: Double,

    val favorite: Boolean = false
)