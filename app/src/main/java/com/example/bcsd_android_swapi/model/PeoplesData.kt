package com.example.bcsd_android_swapi.model

import android.net.Uri
import com.example.bcsd_android_swapi.R
import com.google.gson.annotations.SerializedName

data class PeoplesData(
    @SerializedName("name") var name: String,
    @SerializedName("birth_year") var birthYear: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("height") var height:String,
    @SerializedName("mass") var mass:String,
    val image: Int = R.drawable.ic_baseline_people_alt_24
)
