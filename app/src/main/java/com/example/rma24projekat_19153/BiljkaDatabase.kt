package com.example.rma24projekat_19153

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(Converters::class)

abstract class BiljkaDatabase : RoomDatabase() {
    abstract fun biljkaDao(): BiljkaDAO

    companion object {
        @Volatile
        private var Instance: BiljkaDatabase?= null

        fun getDatabase(context: Context): BiljkaDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BiljkaDatabase::class.java,
                    "biljke-db"
                ).build()
                Instance = instance
                instance
            }
        }
    }

}