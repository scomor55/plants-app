package com.example.rma24projekat_19153

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Biljka::class,
            parentColumns = ["id"],
            childColumns = ["biljkaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["biljkaId"], unique = true)]
)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "biljkaId") val biljkaId: Int,
    @ColumnInfo(name = "bitmap") val bitmap: Bitmap
)