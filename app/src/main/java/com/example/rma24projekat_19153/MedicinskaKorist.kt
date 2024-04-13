package com.example.rma24projekat_19153

import android.os.Parcel
import android.os.Parcelable

enum class MedicinskaKorist(val opis: String):Biljka.ParcelableEnum {
    SMIRENJE("Smirenje - za smirenje i relaksaciju"),
    PROTUUPALNO("Protuupalno - za smanjenje upale"),
    PROTIVBOLOVA("Protivbolova - za smanjenje bolova"),
    REGULACIJAPRITISKA("Regulacija pritiska - za regulaciju visokog/niskog pritiska"),
    REGULACIJAPROBAVE("Regulacija probave"),
    PODRSKAIMUNITETU("Podrška imunitetu"),
    IMMUNOSUPORT("Podrška imunitetu");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<MedicinskaKorist> {
        override fun createFromParcel(parcel: Parcel): MedicinskaKorist {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<MedicinskaKorist?> {
            return arrayOfNulls(size)
        }
    }
}
