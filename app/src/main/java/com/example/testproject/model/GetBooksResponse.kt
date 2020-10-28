package com.example.testproject.model


import com.google.gson.annotations.SerializedName

data class GetBooksResponse(

    @SerializedName("resultCount")
    val resultCount: Int,

    @SerializedName("results")
    val booksRemote: List<BookRemote>
)