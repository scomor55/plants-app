package com.example.rma24projekat_19153

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {


    @GET("plants/search")
    fun searchPlants(@Query("q") query: String): Call<GetPlantsResponse>

    @GET("plants/{id}")
    fun getPlantById(@Path("id") id: Long): Call<PlantResponse>

    @GET("plants/search")
    fun searchPlantsByColorAndSubstr(@Query("filter[flower_color]") flowerColor: String): Call<PlantResponse>
}