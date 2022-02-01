package com.example.bcsd_android_swapi.model

import com.example.bcsd_android_swapi.R
import com.google.gson.annotations.SerializedName

data class FilmsData(
    @SerializedName("title") var title: String,
    @SerializedName("director") var director: String,
    @SerializedName("release_date") var releaseDate: String,
    val image: Int = R.drawable.ic_baseline_movie_24
)
