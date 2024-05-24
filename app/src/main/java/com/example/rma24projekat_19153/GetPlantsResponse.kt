package com.example.rma24projekat_19153

import com.google.gson.annotations.SerializedName

data class GetPlantsResponse(
    @SerializedName("data") val data: List<Plant>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)