package com.example.rma24projekat_19153

import android.os.Parcel
import android.os.Parcelable


enum class KlimatskiTip(val opis: String): Biljka.ParcelableEnum {
    SREDOZEMNA("Mediteranska klima - suha, topla ljeta i blage zime"),
    TROPSKA("Tropska klima - topla i vlažna tokom cijele godine"),
    SUBTROPSKA("Subtropska klima - blage zime i topla do vruća ljeta"),
    UMJERENA("Umjerena klima - topla ljeta i hladne zime"),
    SUHA("Sušna klima - niske padavine i visoke temperature tokom cijele godine"),
    PLANINSKA("Planinska klima - hladne temperature i kratke sezone rasta");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<KlimatskiTip> {
        override fun createFromParcel(parcel: Parcel): KlimatskiTip {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<KlimatskiTip?> {
            return arrayOfNulls(size)
        }
    }
}