package com.example.rma24projekat_19153

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

/*import android.widget.Spinner*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        //Spinner controller
        val spinner = findViewById<Spinner>(R.id.modSpinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity ,
                    "Izabrali ste ${adapterView?.getItemAtPosition(position).toString()}"
                    ,Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        //End of spinner controller

        val biljke = listOf(
            Biljka(
                naziv = "Bosiljak (Ocimum basilicum)",
                porodica = "Lamiaceae (usnate)",
                medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
                medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
                profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
                jela = listOf("Salata od paradajza", "Punjene tikvice"),
                klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
                zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.ILOVICA)
            ),
            Biljka(
                naziv = "Nana (Mentha spicata)",
                porodica = "Lamiaceae (metvice)",
                medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
                medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
                profilOkusa = ProfilOkusaBiljke.MENTA,
                jela = listOf("Jogurt sa voćem", "Gulaš"),
                klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.GLINENO, Zemljište.CRNICA)
            ),
            Biljka(
                naziv = "Kamilica (Matricaria chamomilla)",
                porodica = "Asteraceae (glavočike)",
                medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
                medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Čaj od kamilice"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
                zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Ružmarin (Rosmarinus officinalis)",
                porodica = "Lamiaceae (metvice)",
                medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
                medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Pečeno pile", "Grah","Gulaš"),
                klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
                zemljisniTipovi = listOf(Zemljište.SLJUNKOVITO, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Lavanda (Lavandula angustifolia)",
                porodica = "Lamiaceae (metvice)",
                medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
                medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.IMMUNOSUPORT),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Jogurt sa voćem"),
                klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
                zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Hibiskus (Hibiscus sabdariffa)",
                porodica = "Malvaceae (sljezovke)",
                medicinskoUpozorenje = "Hibiskus čaj se obično smatra sigurnim, ali osobe koje uzimaju lijekove za snižavanje krvnog tlaka trebaju biti oprezne.",
                medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.IMMUNOSUPORT),
                profilOkusa = ProfilOkusaBiljke.CITRUSNI,
                jela = listOf("Torta od limuna i hibiskusa"),
                klimatskiTipovi = listOf(KlimatskiTip.TROPSKA, KlimatskiTip.SUBTROPSKA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Šafran (Crocus sativus)",
                porodica = "Iridaceae (perunike)",
                medicinskoUpozorenje = "Šafran se obično koristi kao začin i nema posebnih upozorenja.",
                medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
                profilOkusa = ProfilOkusaBiljke.LJUTO,
                jela = listOf("Juha od luka sa šafranom"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Kopriva (Urtica dioica)",
                porodica = "Urticaceae (koprive)",
                medicinskoUpozorenje = "Kopriva može izazvati alergijske reakcije kod osjetljivih osoba.",
                medicinskeKoristi = listOf(MedicinskaKorist.IMMUNOSUPORT),
                profilOkusa = ProfilOkusaBiljke.MENTA,
                jela = listOf("Pire od koprive"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Kadulja (Salvia officinalis)",
                porodica = "Lamiaceae (metvice)",
                medicinskoUpozorenje = "Kadulja se obično koristi kao začin i nema posebnih upozorenja.",
                medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Tjestenina u umaku od maslaca i kadulje"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Kadulja (Salvia officinalis)",
                porodica = "Lamiaceae (kaćunovke)",
                medicinskoUpozorenje = "Kadulja se obično koristi kao začin i nema posebnih upozorenja.",
                medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Tjestenina u umaku od maslaca i kadulje"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            ),
            Biljka(
                naziv = "Orhideja  (Orchidaceae)",
                porodica = "Orchidaceae (metvice)",
                medicinskoUpozorenje = "Kadulja se obično koristi kao začin i nema posebnih upozorenja.",
                medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Tjestenina u umaku od maslaca i kadulje"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            )
        )


    }
}