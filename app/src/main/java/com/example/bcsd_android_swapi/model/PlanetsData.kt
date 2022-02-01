package com.example.bcsd_android_swapi.model

import com.example.bcsd_android_swapi.R
import com.google.gson.annotations.SerializedName

data class PlanetsData(
    @SerializedName("name") var name:String,
    @SerializedName("climate") var climate:String,
    @SerializedName("population") var population:String,
    val image:Int = R.drawable.ic_baseline_cloud_circle_24
)
