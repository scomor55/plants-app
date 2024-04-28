package com.example.rma24projekat_19153

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class NovaBiljkaActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var slikaIV: ImageView
    private lateinit var textViewMedicinskaKorist: TextView
    private lateinit var textViewKlimatskiTip: TextView
    private lateinit var textViewZemljisniTip: TextView
    private lateinit var textViewProfilOkusa: TextView
    private lateinit var textViewJelo: TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        textViewMedicinskaKorist = findViewById<TextView>(R.id.labelMedicinskaKorist)
        textViewMedicinskaKorist.visibility = View.INVISIBLE
        textViewKlimatskiTip = findViewById<TextView>(R.id.labelKlimatskiTip)
        textViewKlimatskiTip.visibility = View.INVISIBLE
        textViewZemljisniTip = findViewById<TextView>(R.id.labelZemljisniTip)
        textViewZemljisniTip.visibility = View.INVISIBLE
        textViewProfilOkusa = findViewById<TextView>(R.id.labelProfilOkusa)
        textViewProfilOkusa.visibility = View.INVISIBLE
        textViewJelo = findViewById<TextView>(R.id.labelJelo)
        textViewJelo.visibility = View.INVISIBLE


        val listViewMedicinskaKorist = findViewById<ListView>(R.id.medicinskaKoristLV)
        val enumValuesMedicinskaKorist = MedicinskaKorist.values()
        val adapterMedicinskaKorist = MedicinskaKoristAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesMedicinskaKorist)
        listViewMedicinskaKorist.adapter = adapterMedicinskaKorist
        listViewMedicinskaKorist.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val listViewKlimatskiTip = findViewById<ListView>(R.id.klimatskiTipLV)
        val enumValuesKlimatskiTip = KlimatskiTip.values()
        val adapterKlimatskiTip = KlimatskiTipAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesKlimatskiTip)
        listViewKlimatskiTip.adapter = adapterKlimatskiTip
        listViewKlimatskiTip.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val listViewZemljisniTip = findViewById<ListView>(R.id.zemljisniTipLV)
        val enumValuesZemljisniTip = Zemljište.values()
        val adapterZemljisniTip = ZemljisniTipAdapter(this,android.R.layout.simple_list_item_multiple_choice,enumValuesZemljisniTip)
        listViewZemljisniTip.adapter = adapterZemljisniTip
        listViewZemljisniTip.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val listViewProfilOkusa = findViewById<ListView>(R.id.profilOkusaLV)
        val enumValuesProfilOkusa = ProfilOkusaBiljke.values()
        val adapterProfilOkusa = ProfilOkusaAdapter(this,android.R.layout.simple_list_item_single_choice,enumValuesProfilOkusa)
        listViewProfilOkusa.adapter = adapterProfilOkusa
        listViewProfilOkusa.choiceMode = ListView.CHOICE_MODE_SINGLE

        val dodajJeloBtn = findViewById<Button>(R.id.dodajJeloBtn)
        val jelaListView = findViewById<ListView>(R.id.jelaLV)
        val jeloET = findViewById<EditText>(R.id.jeloET)
        val jelaAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1)
        jelaListView.adapter = jelaAdapter
        //jelaListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        var selectedPosition = ListView.INVALID_POSITION
        dodajJeloBtn.setOnClickListener {
            val novoJelo = jeloET.text.toString().trim()

            if (novoJelo.isNotEmpty() && dodajJeloBtn.text == "Dodaj jelo") {
                 if(sameMeals(findViewById<EditText>(R.id.jeloET),jelaAdapter)) {
                     jelaAdapter.add(novoJelo)
                 }
            }
            if (dodajJeloBtn.text == "Izmijeni jelo"){
                if (novoJelo.isNotEmpty()) {
                    if (selectedPosition != ListView.INVALID_POSITION) {
                        if(sameMeals(findViewById<EditText>(R.id.jeloET),jelaAdapter)) {
                            jelaAdapter.remove(jelaAdapter.getItem(selectedPosition))
                            jelaAdapter.insert(novoJelo, selectedPosition)
                            dodajJeloBtn.text = "Dodaj jelo"
                            jelaListView.clearChoices()
                            selectedPosition = ListView.INVALID_POSITION
                        }

                    }
                }else {
                    jelaAdapter.remove(jelaAdapter.getItem(selectedPosition))
                    jelaListView.clearChoices()
                    dodajJeloBtn.text = "Dodaj jelo"
                    jelaListView.clearChoices()
                    selectedPosition = ListView.INVALID_POSITION
                }
             }
        }

        jelaListView.setOnItemClickListener { parent, view, position, id ->
            selectedPosition = position
            val selectedItem = parent.getItemAtPosition(position).toString()
            jeloET.setText(selectedItem)
            dodajJeloBtn.text = "Izmijeni jelo"

        }
       /* val jelaAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice)
        jelaListView.adapter = jelaAdapter
     Ovo pogledaj   jelaListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE*/

        val uslikajBiljkuBtn = findViewById<Button>(R.id.uslikajBiljkuBtn)
        slikaIV = findViewById(R.id.slikaIV)

        uslikajBiljkuBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }

        val dodajBiljkuBtn = findViewById<Button>(R.id.dodajBiljkuBtn)
        dodajBiljkuBtn.setOnClickListener {

            if (listViewMedicinskaKorist.checkedItemCount != 0){
                textViewMedicinskaKorist.visibility = View.INVISIBLE
            }

            if (listViewKlimatskiTip.checkedItemCount != 0){
                textViewKlimatskiTip.visibility = View.INVISIBLE
            }

            if (listViewZemljisniTip.checkedItemCount != 0){
                textViewZemljisniTip.visibility = View.INVISIBLE
            }

            if (listViewProfilOkusa.checkedItemPosition != ListView.INVALID_POSITION){
                textViewProfilOkusa.visibility = View.INVISIBLE
            }

            if (jelaAdapter.count != 0){
                textViewJelo.visibility = View.INVISIBLE
            }

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
            jelaListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

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

            if(validation(findViewById<EditText>(R.id.nazivET),
                    findViewById<EditText>(R.id.porodicaET),
                    findViewById<EditText>(R.id.medicinskoUpozorenjeET),
                    findViewById<EditText>(R.id.jeloET),
                    listViewMedicinskaKorist,
                    listViewKlimatskiTip,
                    listViewZemljisniTip,
                    listViewProfilOkusa,
                    jelaAdapter)){
                textViewMedicinskaKorist.visibility = View.INVISIBLE
                textViewKlimatskiTip.visibility = View.INVISIBLE
                textViewZemljisniTip.visibility = View.INVISIBLE
                textViewProfilOkusa.visibility = View.INVISIBLE
                textViewJelo.visibility = View.INVISIBLE

                val allPlants = intent.getSerializableExtra("allPlants") as? List<Biljka>
                val novaBiljka = Biljka(nazivBiljke,porodica,medicinskoUpozorenje,selectedMedicinskaKorist,selectedProfilOkusa.first(),selectedJela,selectedKlimatskiTip,selectedZemljisniTip)
                val newPlantsList = allPlants?.toMutableList()
                newPlantsList?.add(novaBiljka)
               val returnIntent = Intent(this, MainActivity::class.java)
                returnIntent.putExtra("novaLista",newPlantsList as Serializable)
               startActivity(returnIntent)
                finish()
            }else {
                Toast.makeText(this@NovaBiljkaActivity, "Molimo popunite sva polja", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun validation(
        nazivBiljke: EditText,
        porodica: EditText,
        medicinskoUpozorenje: EditText,
        jelo: EditText,
        listViewMedicinskaKorist: ListView,
        listViewKlimatskiTip: ListView,
        listViewZemljisniTip: ListView,
        listViewProfilOkusa: ListView,
        jelaAdapter: ArrayAdapter<String>
        ):Boolean {
        var isValid = true

        if (nazivBiljke.text.length < 2 || nazivBiljke.text.length > 20) {
            nazivBiljke.error = "Naziv biljke mora imati između 2 i 20 znakova"
            isValid = false
        }
        if (porodica.text.length < 2 || porodica.text.length > 20) {
            porodica.error = "Porodica mora imati između 2 i 20 znakova"
            isValid = false
        }

        if (medicinskoUpozorenje.text.length < 3 || medicinskoUpozorenje.text.length > 20) {
            medicinskoUpozorenje.error = "Medicinsko upozorenje mora imati između 2 i 20 znakova"
            isValid = false
        }

        if (listViewMedicinskaKorist.checkedItemCount == 0) {
            textViewMedicinskaKorist = findViewById<TextView>(R.id.labelMedicinskaKorist)
            textViewMedicinskaKorist.visibility = View.VISIBLE

        //    Toast.makeText(this, "Odaberite barem jednu medicinsku korist", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (listViewKlimatskiTip.checkedItemCount == 0) {

            textViewKlimatskiTip = findViewById<TextView>(R.id.labelKlimatskiTip)
            textViewKlimatskiTip.visibility = View.VISIBLE

        //    Toast.makeText(this, "Odaberite barem jedan klimatski tip", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (listViewZemljisniTip.checkedItemCount == 0) {
            textViewZemljisniTip = findViewById<TextView>(R.id.labelZemljisniTip)
            textViewZemljisniTip.visibility = View.VISIBLE

        //    Toast.makeText(this, "Odaberite barem jedan zemljisni tip", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (listViewProfilOkusa.checkedItemPosition == ListView.INVALID_POSITION) {
            textViewProfilOkusa = findViewById<TextView>(R.id.labelProfilOkusa)
            textViewProfilOkusa.visibility = View.VISIBLE

        //    Toast.makeText(this, "Odaberite profil okusa", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (jelaAdapter.count == 0) {
            textViewJelo = findViewById<TextView>(R.id.labelJelo)
            textViewJelo.visibility = View.VISIBLE

        //    Toast.makeText(this, "Dodajte barem jedno jelo", Toast.LENGTH_SHORT).show()
            isValid = false
        }


        return isValid
    }

    private fun sameMeals(jelo: EditText,jelaAdapter: ArrayAdapter<String>):Boolean {
        for (i in 0 until jelaAdapter.count){
                if (jelo.text.toString().lowercase() == jelaAdapter.getItem(i)?.lowercase()){
                    jelo.error = "Jelo je vec dodano"
                    return false
                }
        }
        if (jelo.text.length < 2 || jelo.text.length > 20) {
            jelo.error = "Jelo mora imati između 2 i 20 znakova"
            return false
        }
        return true
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