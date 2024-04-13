package com.example.rma24projekat_19153

import android.os.Parcel
import android.os.Parcelable

data class Biljka(
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljište>
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(MedicinskaKorist)!!,
        enumValueOf<ProfilOkusaBiljke>(parcel.readString()!!),
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(KlimatskiTip)!!,
        parcel.createTypedArrayList(Zemljište)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naziv)
        parcel.writeString(porodica)
        parcel.writeString(medicinskoUpozorenje)
        parcel.writeTypedList(medicinskeKoristi)
        parcel.writeString(profilOkusa.name)
        parcel.writeStringList(jela)
        parcel.writeTypedList(klimatskiTipovi)
        parcel.writeTypedList(zemljisniTipovi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Biljka> {
        override fun createFromParcel(parcel: Parcel): Biljka {
            return Biljka(parcel)
        }

        override fun newArray(size: Int): Array<Biljka?> {
            return arrayOfNulls(size)
        }
    }

    interface ParcelableEnum : Parcelable {
        override fun describeContents(): Int {
            return 0
        }

        companion object {
            inline fun <reified T : Enum<T>> createEnumValuesParceler(crossinline creator: (String) -> T?): Parcelable.Creator<T> =
                object : Parcelable.Creator<T> {
                    override fun createFromParcel(parcel: Parcel): T? = creator(parcel.readString()!!)
                    override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
                }
        }
    }
}
