package com.example.bcsd_android_swapi

import com.example.bcsd_android_swapi.model.FilmsData
import com.example.bcsd_android_swapi.model.PeoplesData
import com.example.bcsd_android_swapi.model.PlanetsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("people/1")
    fun getUser(): Call<PeoplesData>

    @GET("people/{page}")
    fun getPeoplesPage(@Path("page") page:String): Call<PeoplesData>

    @GET("planets/{page}")
    fun getPlanetsPage(@Path("page") page:String): Call<PlanetsData>

    @GET("films/{page}")
    fun getFilmsPage(@Path("page") page:String): Call<FilmsData>
}

