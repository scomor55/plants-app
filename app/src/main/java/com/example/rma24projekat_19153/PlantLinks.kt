package com.example.rma24projekat_19153

import com.google.gson.annotations.SerializedName

data class PlantLinks(
    @SerializedName("self") val self: String,
    @SerializedName("plant") val plant: String,
    @SerializedName("genus") val genus: String
)
