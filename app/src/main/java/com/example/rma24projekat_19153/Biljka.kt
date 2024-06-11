package com.example.rma24projekat_19153

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Biljka(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "naziv") var naziv: String,
    @ColumnInfo(name = "porodica") var porodica: String,
    @ColumnInfo(name = "medicinskoUpozorenje") var medicinskoUpozorenje: String,
    @ColumnInfo(name = "medicinskeKoristi") val medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "profilOkusa") val profilOkusa: ProfilOkusaBiljke,
    @ColumnInfo(name = "jela") var jela: List<String>,
    @ColumnInfo(name = "klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo(name = "zemljisniTipovi") var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name = "onlineChecked") var onlineChecked: Boolean = false
):Serializable {
    constructor(
        naziv: String,
        porodica: String,
        medicinskoUpozorenje: String,
        medicinskeKoristi: List<MedicinskaKorist>,
        profilOkusa: ProfilOkusaBiljke,
        jela: List<String>,
        klimatskiTipovi: List<KlimatskiTip>,
        zemljisniTipovi: List<Zemljiste>,
        onlineChecked: Boolean = false
    ) : this(
        0,
        naziv,
        porodica,
        medicinskoUpozorenje,
        medicinskeKoristi,
        profilOkusa,
        jela,
        klimatskiTipovi,
        zemljisniTipovi,
        onlineChecked
    )
}
