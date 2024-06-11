package com.example.rma24projekat_19153

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BiljkaBitmap(
    @PrimaryKey val idBiljke: Int,
    val bitmap: Bitmap
)