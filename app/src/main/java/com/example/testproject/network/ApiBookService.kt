package com.example.testproject.network

import com.example.testproject.model.GetBooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiBookService {

    @GET("search")
    suspend fun searchBook(
        @Query("term") searchValue: String,
        @Query("media") media: String,
        @Query("country") country: String,
        @Query("limit") limit: Int,
    ): Response<GetBooksResponse>
}