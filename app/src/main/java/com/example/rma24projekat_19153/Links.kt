package com.example.rma24projekat_19153

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self") val self: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String,
    @SerializedName("next") val next: String?
)