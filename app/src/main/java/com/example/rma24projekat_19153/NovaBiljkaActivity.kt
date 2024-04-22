package com.example.rma24projekat_19153

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class NovaBiljkaActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var slikaIV: ImageView
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        val listViewMedicinskaKorist = findViewById<ListView>(R.id.medicinskaKoristLV)
        val enumValuesMedicinskaKorist = MedicinskaKorist.values()
        val adapterMedicinskaKorist = MedicinskaKoristAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesMedicinskaKorist)
        listViewMedicinskaKorist.adapter = adapterMedicinskaKorist

        val listViewKlimatskiTip = findViewById<ListView>(R.id.klimatskiTipLV)
        val enumValuesKlimatskiTip = KlimatskiTip.values()
        val adapterKlimatskiTip = KlimatskiTipAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesKlimatskiTip)
        listViewKlimatskiTip.adapter = adapterKlimatskiTip

        val listViewZemljisniTip = findViewById<ListView>(R.id.zemljisniTipLV)
        val enumValuesZemljisniTip = Zemljište.values()
        val adapterZemljisniTip = ZemljisniTipAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesZemljisniTip)
        listViewZemljisniTip.adapter = adapterZemljisniTip

        val listViewProfilOkusa = findViewById<ListView>(R.id.profilOkusaLV)
        val enumValuesProfilOkusa = ProfilOkusaBiljke.values()
        val adapterProfilOkusa = ProfilOkusaAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesProfilOkusa)
        listViewProfilOkusa.adapter = adapterProfilOkusa

        val dodajJeloBtn = findViewById<Button>(R.id.dodajJeloBtn)
        val jelaListView = findViewById<ListView>(R.id.jelaLV)
        val jeloET = findViewById<EditText>(R.id.jeloET)
        dodajJeloBtn.setOnClickListener {
            val novoJelo = jeloET.text.toString().trim()
            if (novoJelo.isNotEmpty()) {
                val selectedItemPosition = jelaListView.checkedItemPosition
                if (selectedItemPosition != ListView.INVALID_POSITION) {
                    val adapter = jelaListView.adapter as ArrayAdapter<String>
                    adapter.getItem(selectedItemPosition)?.let {
                        adapter.remove(it)
                        adapter.insert(novoJelo, selectedItemPosition)
                        jeloET.setText("")
                        dodajJeloBtn.text = "Dodaj jelo"
                    }
                } else {
                    val adapter = jelaListView.adapter as ArrayAdapter<String>
                    adapter.add(novoJelo)
                    jeloET.setText("")
                    dodajJeloBtn.text = "Dodaj jelo"
                }
                jelaListView.clearChoices()
            } else {
                val selectedItemPosition = jelaListView.checkedItemPosition
                if (selectedItemPosition != ListView.INVALID_POSITION) {
                    val adapter = jelaListView.adapter as ArrayAdapter<String>
                    adapter.remove(adapter.getItem(selectedItemPosition))
                    jeloET.setText("")
                    dodajJeloBtn.text = "Dodaj jelo"
                }
            }
        }

        jelaListView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            jeloET.setText(selectedItem)
            dodajJeloBtn.text = "Izmijeni jelo"
        }
        val jelaAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice)
        jelaListView.adapter = jelaAdapter


        val uslikajBiljkuBtn = findViewById<Button>(R.id.uslikajBiljkuBtn)
        slikaIV = findViewById(R.id.slikaIV)

        uslikajBiljkuBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }

        val dodajBiljkuBtn = findViewById<Button>(R.id.dodajBiljkuBtn)
        dodajBiljkuBtn.setOnClickListener {
            val nazivBiljke = findViewById<EditText>(R.id.nazivET).text.toString()
            val porodica = findViewById<EditText>(R.id.porodicaET).text.toString()
            val medicinskoUpozorenje = findViewById<EditText>(R.id.medicinskoUpozorenjeET).text.toString()
            val jelo = findViewById<EditText>(R.id.jeloET).text.toString()

            val selectedMedicinskaKorist = mutableListOf<MedicinskaKorist>()
            val medicinskaKoristCheckedPositions = listViewMedicinskaKorist.checkedItemPositions
            for(i in 0 until medicinskaKoristCheckedPositions.size()){
                if(medicinskaKoristCheckedPositions.valueAt(i)){
                    val position = medicinskaKoristCheckedPositions.keyAt(i)
                    val korist = enumValuesMedicinskaKorist[position]
                    selectedMedicinskaKorist.add(korist)
                }
            }

            val selectedKlimatskiTip = mutableListOf<KlimatskiTip>()
            val klimatskiTipCheckedPositions = listViewKlimatskiTip.checkedItemPositions
            for(i in 0 until klimatskiTipCheckedPositions.size()){
                if(klimatskiTipCheckedPositions.valueAt(i)){
                    val position = klimatskiTipCheckedPositions.keyAt(i)
                    val tip = enumValuesKlimatskiTip[position]
                    selectedKlimatskiTip.add(tip)
                }
            }

            val selectedZemljisniTip = mutableListOf<Zemljište>()
            val zemljisniTipCheckedPositions = listViewZemljisniTip.checkedItemPositions
            for(i in 0 until zemljisniTipCheckedPositions.size()){
                if(zemljisniTipCheckedPositions.valueAt(i)){
                    val position = zemljisniTipCheckedPositions.keyAt(i)
                    val tip = enumValuesZemljisniTip[position]
                    selectedZemljisniTip.add(tip)
                }
            }

            val selectedProfilOkusa = mutableListOf<ProfilOkusaBiljke>()
            val profilOkusaCheckedPositions = listViewProfilOkusa.checkedItemPositions
            for(i in 0 until profilOkusaCheckedPositions.size()){
                if(profilOkusaCheckedPositions.valueAt(i)){
                    val position = profilOkusaCheckedPositions.keyAt(i)
                    val profil = enumValuesProfilOkusa[position]
                    selectedProfilOkusa.add(profil)
                }
            }
            val selectedJela = mutableListOf<String>()
            for (i in 0 until jelaAdapter.count){
                jelaAdapter.getItem(i)?.let { selectedItem ->
                    selectedJela.add(selectedItem)
                }
            }
            val dummyNewPlant = Biljka(
                naziv = "Nova biljka",
                porodica = "Nova porodica",
                medicinskoUpozorenje = "Upozorenje za novu biljku",
                medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE),
                profilOkusa = ProfilOkusaBiljke.AROMATICNO,
                jela = listOf("Novo jelo"),
                klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
                zemljisniTipovi = listOf(Zemljište.CRNICA, Zemljište.KRECNJACKO)
            )

            if(nazivBiljke.isNotEmpty() && selectedMedicinskaKorist.isNotEmpty() &&
                selectedKlimatskiTip.isNotEmpty() && selectedZemljisniTip.isNotEmpty() &&
                selectedProfilOkusa.isNotEmpty() && selectedJela.isNotEmpty()){

                val allPlants = intent.getSerializableExtra("allPlants") as? List<Biljka>
                val novaBiljka = Biljka(nazivBiljke,porodica,medicinskoUpozorenje,selectedMedicinskaKorist,selectedProfilOkusa.first(),selectedJela,selectedKlimatskiTip,selectedZemljisniTip)
                val newPlantsList = allPlants?.toMutableList()
                newPlantsList?.add(dummyNewPlant)
               val returnIntent = Intent(this, MainActivity::class.java)
                returnIntent.putExtra("novaLista",newPlantsList as Serializable)
               startActivity(returnIntent)
                finish()
            }else {
                Toast.makeText(this@NovaBiljkaActivity, "Molimo popunite sva polja", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            slikaIV.setImageBitmap(imageBitmap)
        }
    }

}