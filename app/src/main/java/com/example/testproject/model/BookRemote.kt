package com.example.testproject.model


import com.google.gson.annotations.SerializedName

data class BookRemote(
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("artistIds")
    val artistIds: List<Int>,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artistViewUrl")
    val artistViewUrl: String,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("fileSizeBytes")
    val fileSizeBytes: Int,
    @SerializedName("formattedPrice")
    val formattedPrice: String,
    @SerializedName("genreIds")
    val genreIds: List<String>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("trackCensoredName")
    val trackCensoredName: String,
    @SerializedName("trackId")
    val trackId: Int,
    @SerializedName("trackName")
    val trackName: String,
    @SerializedName("trackViewUrl")
    val trackViewUrl: String
)