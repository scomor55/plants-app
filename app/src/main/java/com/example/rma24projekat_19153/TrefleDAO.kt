package com.example.rma24projekat_19153


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrefleDAO(private val context: Context ?= null ) {

    private val api = RetrofitClient.retrofit
   private val defaultBitmap: Bitmap = BitmapFactory.decodeResource(context?.resources , R.drawable.biljka)

    fun extractTextInBrackets(input: String): String {
        val startIndex = input.indexOf('(')
        val endIndex = input.indexOf(')', startIndex)

        return input.substring(startIndex + 1, endIndex)
    }


    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val latinName = extractTextInBrackets(biljka.naziv)
                val response = api.searchPlants(latinName).execute()
                //  Log.d("API_RESPONSE", "Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val plants = response.body()?.data
                    //   Log.d("API_RESPONSE", "Plants: $plants")
                    if (!plants.isNullOrEmpty()) {
                        val imageUrl = plants[0].imageUrl
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



    suspend fun fixData(biljka: Biljka): Biljka {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("DAO NAZIV", "NAZIV:  ${biljka.naziv}")
                val latinName = extractTextInBrackets(biljka.naziv)
                Log.d("LATIN", "Latin:  ${latinName}")

                val searchResponse = api.searchPlants(latinName).execute()
                if (searchResponse.isSuccessful) {
                    val plants = searchResponse.body()?.data
                    val plant = plants?.firstOrNull()
                    Log.d("DAO", "Tu saaaaaaaaaam")
                    Log.d("API Response", "Body: ${searchResponse.body()}")

                    if (plant != null) {

                        // Use the ID to get detailed information about the plant
                        val plantId = plant.id
                        val detailResponse = api.getPlantById(plantId).execute()
                        Log.d("API Detail", "Body: ${detailResponse.body()}")
                        Log.d("API Edible", "Edible: ${detailResponse.body()?.data?.mainSpecies?.edible}")
                        Log.d("API Toxic", "TOXIC: ${detailResponse.body()?.data?.mainSpecies?.specifications?.toxicity}")

                        if (detailResponse.isSuccessful) {
                            val detailedPlant = detailResponse.body()
                            //   biljka.naziv = detailedPlant?.data?.commonName.toString()
                            // Fix family
                            if (biljka.porodica != detailedPlant?.data?.family.toString()) {
                                biljka.porodica = detailedPlant?.data?.family?.name.toString()
                            }

                            if (detailedPlant?.data?.mainSpecies?.edible == false || detailedPlant?.data?.mainSpecies?.edible == null) {
                                biljka.jela = listOf()
                                if (!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                                    biljka.medicinskoUpozorenje += " NIJE JESTIVO"
                                }
                            }

                            // Fix medical warning
                            if (detailedPlant?.data?.mainSpecies?.specifications?.toxicity == "null") {
                                if (!biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                                    biljka.medicinskoUpozorenje += " TOKSIČNO"
                                }
                            }

                            // Fix soil types
                            val validSoilTypes = mapOf(
                                Zemljište.SLJUNKOVITO to 9,
                                Zemljište.KRECNJACKO to 10,
                                Zemljište.GLINENO to listOf(1, 2),
                                Zemljište.PJESKOVITO to listOf(3, 4),
                                Zemljište.ILOVACA to listOf(5, 6),
                                Zemljište.CRNICA to listOf(7, 8)
                            )
                            val soilTextures = detailedPlant?.data?.mainSpecies?.growth?.soilTexture
                            //   val soilTextures = 5
                            Log.d("API Soil", "Soil: ${soilTextures}")

                            if (soilTextures != null) {
                                val filteredSoilTypes = mutableListOf<Zemljište>()
                                for ((soilType, validValues) in validSoilTypes) {
                                    if (validValues is Int && validValues == soilTextures) {
                                        filteredSoilTypes.add(soilType)
                                    } else if (validValues is List<*> && validValues.contains(soilTextures)) {
                                        filteredSoilTypes.add(soilType)
                                    }
                                }
                                biljka.zemljisniTipovi = filteredSoilTypes
                            }

                            // Fix climate types
                            val validClimateTypes = mapOf(
                                KlimatskiTip.SREDOZEMNA to (6..9 to 1..5),
                                KlimatskiTip.TROPSKA to (8..10 to 7..10),
                                KlimatskiTip.SUBTROPSKA to (6..9 to 5..8),
                                KlimatskiTip.UMJERENA to (4..7 to 3..7),
                                KlimatskiTip.SUHA to (7..9 to 1..2),
                                KlimatskiTip.PLANINSKA to (0..5 to 3..7)
                            )
                            val light = detailedPlant?.data?.mainSpecies?.growth?.light
                            val humidity = detailedPlant?.data?.mainSpecies?.growth?.atmosphericHumidity

                            //   val light = 5
                            //  val humidity = 5
                            Log.d("API Klima", "light: ${soilTextures}")
                            Log.d("API Klima", "humidity: ${soilTextures}")


                            if (light != null && humidity != null) {
                                val filteredClimateTypes = mutableListOf<KlimatskiTip>()
                                for ((climateType, ranges) in validClimateTypes) {
                                    val (lightRange, humidityRange) = ranges
                                    if (light in lightRange && humidity in humidityRange) {
                                        filteredClimateTypes.add(climateType)
                                    }
                                }
                                biljka.klimatskiTipovi = filteredClimateTypes
                            }
                        }
                    }
                }
                return@withContext biljka
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext biljka
            }
        }
    }

    suspend fun getPlantsWithFlowerColor(flower_color: String, substr: String): List<Biljka> {
        val emptyBiljkaList: MutableList<Biljka> = mutableListOf()
        return withContext(Dispatchers.IO) {
            try {
                val searchResponse = api.searchPlants(substr).execute()
                Log.d("RESPONSE", searchResponse.body()?.data.toString())
                val plantsResponse = searchResponse.body()?.data ?: emptyList()

                val detailList : MutableList<PlantResponse> = mutableListOf()
                for(plant in plantsResponse){
                    val detailResponse = api.getPlantById(plant.id).execute()
                    val plantDetail = detailResponse.body()
                    plantDetail?.let {
                        detailList.add(it)
                    }
                }
                /*  for(detail in detailList){
                      Log.d("FORGORE", detail.data?.mainSpecies?.flower?.color.toString())
                      Log.d("FORGORE", detail.data?.commonName.toString())
                  }*/
                val filteredDetailList = detailList.filter { detail ->
                    when (val color = detail.data?.mainSpecies?.flower?.color) {
                        is String -> color.contains(flower_color, ignoreCase = true)
                        is List<*> -> color.any { it is String && it.contains(flower_color, ignoreCase = true) }
                        else -> false
                    }
                }
                /* for(detail in filteredDetailList){
                     Log.d("FILTRIRANO", detail.data?.mainSpecies?.flower?.color.toString())
                     Log.d("FILTRIRANO", detail.data?.commonName.toString())

                 }*/

                for(plant in filteredDetailList){
                    val newPlant = Biljka(
                        naziv = "${plant.data?.commonName} (${plant.data?.scientificName})",
                        porodica = "${plant.data?.family?.name} (${plant.data?.family?.commonName})",
                        medicinskoUpozorenje = "",
                        medicinskeKoristi = listOf(),
                        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
                        jela = listOf(),
                        klimatskiTipovi = listOf(),
                        zemljisniTipovi = listOf()
                    )
                    emptyBiljkaList.add(newPlant)
                }

                return@withContext emptyBiljkaList
            }catch (e: Exception){
                e.printStackTrace()
                return@withContext emptyBiljkaList
            }
        }
        return emptyBiljkaList
    }
    private fun downloadImage(url: String): Bitmap {
        return try {
            Glide.with(context!!)
                .asBitmap()
                .load(url)
                .apply(RequestOptions().override(600, 600))
                .submit()
                .get()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultBitmap
        }
    }

    suspend fun searchPlantByLatinName(latinName: String, onSuccess: (Plant?) -> Unit, onFailure: (Throwable) -> Unit) {
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


    suspend fun searchPlants(query: String, onSuccess: (List<Plant>) -> Unit, onFailure: (Throwable) -> Unit) {
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