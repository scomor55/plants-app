package com.example.rma24projekat_19153

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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