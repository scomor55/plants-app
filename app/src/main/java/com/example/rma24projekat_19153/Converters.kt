package com.example.rma24projekat_19153

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineStart
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64

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
    fun fromBitmap(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return byteArray.joinToString("") { "%02x".format(it) }
    }

    @TypeConverter
    fun toBitmap(hexString: String): Bitmap {
        val byteArray = hexString.chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}