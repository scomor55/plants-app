package com.example.rma24projekat_19153

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun listToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromMedicinskaKoristList(value: String): List<MedicinskaKorist> {
        val listType = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun medicinskaKoristListToString(list: List<MedicinskaKorist>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromKlimatskiTipList(value: String): List<KlimatskiTip> {
        val listType = object : TypeToken<List<KlimatskiTip>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun klimatskiTipListToString(list: List<KlimatskiTip>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromZemljisteList(value: String): List<Zemljiste> {
        val listType = object : TypeToken<List<Zemljiste>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun zemljisteListToString(list: List<Zemljiste>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}