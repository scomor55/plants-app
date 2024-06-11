package com.example.rma24projekat_19153

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface BiljkaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljka(biljka: Biljka): Long

    @Query("SELECT * FROM Biljka WHERE onlineChecked = false")
    suspend fun getOfflineBiljkas(): List<Biljka>

    @Update
    suspend fun updateBiljkas(biljkas: List<Biljka>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljkaBitmap(BiljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    suspend fun clearAllBiljkas()

    @Query("DELETE FROM biljkaBitmap")
    suspend fun clearAllBitmaps()

    @Transaction
    suspend fun clearData() {
        clearAllBiljkas()
        clearAllBitmaps()
    }

    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return insertBiljka(biljka) > 0
    }

    @Transaction
    suspend fun fixOfflineBiljka(fixData: suspend (Biljka) -> Biljka): Int {
        val offlineBiljkas = getOfflineBiljkas()
        val updatedBiljkas = mutableListOf<Biljka>()

        for (biljka in offlineBiljkas) {
            val originalBiljka = biljka.copy()
            val fixedBiljka = fixData(biljka)

            if (originalBiljka != fixedBiljka) {
                fixedBiljka.onlineChecked = true
                updatedBiljkas.add(fixedBiljka)
            }
        }

        updateBiljkas(updatedBiljkas)
        return updatedBiljkas.size
    }

    suspend fun addImage(idBiljke: Int, bitmap: Bitmap): Boolean {
        val biljkaBitmap = BiljkaBitmap(idBiljke, bitmap)
        return insertBiljkaBitmap(biljkaBitmap) > 0
    }
}