package com.example.rma24projekat_19153

import android.os.Parcel
import android.os.Parcelable

enum class Zemljište(val naziv: String):Biljka.ParcelableEnum {
    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glinеno zemljište"),
    ILOVACA("Ilovača"),
    ILOVICA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNKOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<Zemljište> {
        override fun createFromParcel(parcel: Parcel): Zemljište {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<Zemljište?> {
            return arrayOfNulls(size)
        }
    }
}