package com.example.rma24projekat_19153

import java.io.Serializable

data class Biljka(
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljište>
):Serializable
