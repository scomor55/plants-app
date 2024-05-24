package com.example.rma24projekat_19153

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("plants/search")
    suspend fun searchPlants(@Query("q") query: String): Call<GetPlantsResponse>
}