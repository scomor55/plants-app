package com.example.rma24projekat_19153


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class TrefleDAO(private val api: Api,private val context: Context) {


    private val defaultBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.biljka)

    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.searchPlants(biljka.porodica).execute()
                if (response.isSuccessful) {
                    val plants = response.body()?.data
                    if (!plants.isNullOrEmpty()) {
                        val imageUrl = plants[0].image_url
                        if (!imageUrl.isNullOrEmpty()) {
                            return@withContext downloadImage(imageUrl)
                        }
                    }
                }
                return@withContext defaultBitmap
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext defaultBitmap
            }
        }
    }



    fun searchPlantByLatinName(latinName: String, onSuccess: (Plant?) -> Unit, onFailure: (Throwable) -> Unit) {
        val call = api.searchPlants("latin:$latinName")
        call.enqueue(object : Callback<GetPlantsResponse> {
            override fun onResponse(
                call: Call<GetPlantsResponse>,
                response: Response<GetPlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val plants = response.body()?.data ?: emptyList()
                    val firstPlant = plants.firstOrNull()
                    onSuccess(firstPlant)
                } else {
                    onFailure(Throwable(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<GetPlantsResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }


    fun searchPlants(query: String, onSuccess: (List<Plant>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call = api.searchPlants(query)
        call.enqueue(object : Callback<GetPlantsResponse> {
            override fun onResponse(
                call: Call<GetPlantsResponse>,
                response: Response<GetPlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val plants = response.body()?.data ?: emptyList()
                    onSuccess(plants)
                } else {
                    onFailure(Throwable(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<GetPlantsResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}