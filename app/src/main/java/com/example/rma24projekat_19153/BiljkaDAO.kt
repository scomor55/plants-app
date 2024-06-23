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
    suspend fun insertBiljkaBitmap(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    suspend fun clearAllBiljkas()

    @Query("DELETE FROM BiljkaBitmap")
    suspend fun clearAllBitmaps()

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke")
    suspend fun getBiljkaBitmap(idBiljke: Int): BiljkaBitmap?

    @Transaction
    suspend fun clearData() {
        clearAllBiljkas()
        clearAllBitmaps()
    }

    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return insertBiljka(biljka) > 0
    }

    @Transaction
    suspend fun fixOfflineBiljka(): Int {
        val offlineBiljkas = getOfflineBiljkas()
        val updatedBiljkas = mutableListOf<Biljka>()
        val trefleDAO = TrefleDAO()

        for (biljka in offlineBiljkas) {
            val originalBiljka = biljka.copy()
            val fixedBiljka = trefleDAO.fixData(biljka)

            if (originalBiljka != fixedBiljka) {
                fixedBiljka.onlineChecked = true
                updatedBiljkas.add(fixedBiljka)
            }
        }

        updateBiljkas(updatedBiljkas)
        return updatedBiljkas.size
    }

    suspend fun addImage(idBiljke: Int, bitmap: Bitmap): Boolean {
        val biljkaBitmap = BiljkaBitmap(biljkaId = idBiljke, bitmap = bitmap)
        return insertBiljkaBitmap(biljkaBitmap) > 0
    }
}