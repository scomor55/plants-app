package com.example.rma24projekat_19153

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var plants: RecyclerView
    private lateinit var currentPlantsAdapter: MedicalPlantsListAdapter
    val listOfPlants = getPlants().toMutableList()
    private lateinit var resetButton: Button
    private lateinit var medicalPlantsAdapter: MedicalPlantsListAdapter
    private lateinit var cookingPlantsAdapter: CookingPlantsListAdapter
    private lateinit var botanicPlantsAdapter: BotanicPlantsListAdapter
    private lateinit var similarPlants: List <Biljka>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val trefleDAO = TrefleDAO(RetrofitClient.retrofit,this)


        val biljka = Biljka(
            naziv = "Safet",
            porodica = "Solanum tuberosum", // Primjer porodice
            medicinskoUpozorenje = "Gorko",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.LJUTO,
            jela = listOf("Pesto", "Salata"), // Primjer jela,
            zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.CRNICA),
            klimatskiTipovi = listOf(KlimatskiTip.TROPSKA, KlimatskiTip.SUBTROPSKA)
        )

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val fixedBiljka = trefleDAO.fixData(biljka)
                Log.d("FixedBiljka", "Naziv: ${fixedBiljka.naziv}")
                Log.d("FixedBiljka", "Porodica: ${fixedBiljka.porodica}")
                Log.d("FixedBiljka", "Jela: ${fixedBiljka.jela}")
                Log.d("FixedBiljka", "Medicinsko upozorenje: ${fixedBiljka.medicinskoUpozorenje}")
                Log.d("FixedBiljka", "Zemljisni tipovi: ${fixedBiljka.zemljisniTipovi}")
                Log.d("FixedBiljka", "Klimatski tipovi: ${fixedBiljka.klimatskiTipovi}")
            } catch (e: Exception) {
                Log.e("Error", "Error: ${e.message}")
            }
        }


        /* trefleDAO.searchPlants("Ocimum basilicum",
             onSuccess = {plants ->
                for (plant in plants) {
                     Log.d("Plant", "${plants[0].scientificName} - ${plants[0].commonName}")
                }
             },
             onFailure = { error ->
                 Log.e("Error", "Error: ${error.message}")
             })*/





        if(intent.hasExtra("novaLista")){
            val newPlantsList = intent.getSerializableExtra("novaLista") as? List<Biljka>
            if(newPlantsList != null){
                listOfPlants.clear()
                listOfPlants.addAll(newPlantsList)
            }
        }

        plants = findViewById(R.id.biljkeRV)
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        plants.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        similarPlants = listOf()
        //Spinner controller

        val spinner = findViewById<Spinner>(R.id.modSpinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity ,
                    "Izabrali ste ${adapterView?.getItemAtPosition(position).toString()}"
                    ,Toast.LENGTH_LONG).show()

                when(selectedItem){
                    "Medicinski" -> {
                        medicalPlantsAdapter = MedicalPlantsListAdapter(listOf(),trefleDAO)
                        plants.adapter = medicalPlantsAdapter
                        if(similarPlants.isNotEmpty()){
                            medicalPlantsAdapter.updatePlants(similarPlants)
                        }else{
                            medicalPlantsAdapter.updatePlants(listOfPlants)

                        }
                        medicalPlantsAdapter.setOnPlantItemClickListener(object: MedicalPlantsListAdapter.PlantItemClickListener {
                            override fun onPlantItemClick(plant: Biljka) {
                                similarPlants = listOfPlants.filter {
                                    it.medicinskeKoristi.intersect(plant.medicinskeKoristi)
                                        .isNotEmpty()
                                }
                                medicalPlantsAdapter.updatePlants(similarPlants)
                            }
                        }
                        )

                    }
                    "Kuharski" -> {
                        cookingPlantsAdapter = CookingPlantsListAdapter(listOf())
                        plants.adapter = cookingPlantsAdapter
                        if(similarPlants.isNotEmpty()){
                            cookingPlantsAdapter.updatePlants(similarPlants)
                        }else{
                            cookingPlantsAdapter.updatePlants(listOfPlants)
                        }
                        cookingPlantsAdapter.setOnPlantItemClickListener(object : CookingPlantsListAdapter.PlantItemClickListener{
                            override fun onPlantItemClick(plant: Biljka) {
                                var similarPlantsTaste = listOfPlants.filter { it.profilOkusa == plant.profilOkusa }
                                var similarPlantsByDish = listOfPlants.filter {it.jela.intersect(plant.jela).isNotEmpty()
                                }
                                similarPlants = (similarPlantsByDish + similarPlantsTaste).distinct()
                                cookingPlantsAdapter.updatePlants(similarPlants)
                            }
                        })
                    }
                    "Botanički" ->{
                        botanicPlantsAdapter = BotanicPlantsListAdapter(listOf())
                        plants.adapter = botanicPlantsAdapter
                        if(similarPlants.isNotEmpty()){
                            botanicPlantsAdapter.updatePlants(similarPlants)
                        }else{
                            botanicPlantsAdapter.updatePlants(listOfPlants)
                        }
                        botanicPlantsAdapter.setOnPlantItemClickListener(object : BotanicPlantsListAdapter.PlantItemClickListener{
                            override fun onPlantItemClick(plant: Biljka) {
                                similarPlants = listOfPlants.filter {
                                    it.porodica == plant.porodica &&
                                            it.klimatskiTipovi.any{it in plant.klimatskiTipovi} &&
                                            it.zemljisniTipovi.any{it in plant.zemljisniTipovi}
                                }
                                botanicPlantsAdapter.updatePlants(similarPlants)
                            }
                        })
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        resetButton = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            // Reset plants adapter to medicalPlantsAdapter
            similarPlants = listOf()
            val selectedItem = spinner.selectedItem.toString()
            when(selectedItem){
                "Medicinski" -> {
                    plants.adapter = medicalPlantsAdapter
                    medicalPlantsAdapter.updatePlants(listOfPlants)
                }
                "Kuharski" -> {
                    plants.adapter = cookingPlantsAdapter
                    cookingPlantsAdapter.updatePlants(listOfPlants)
                }
                "Botanički" -> {
                    plants.adapter = botanicPlantsAdapter
                    botanicPlantsAdapter.updatePlants(listOfPlants)
                }
            }
            /* plants.adapter = medicalPlantsAdapter
             medicalPlantsAdapter.updatePlants(listOfPlants)*/
            spinner.setSelection(spinner.selectedItemPosition)
        }
        medicalPlantsAdapter = MedicalPlantsListAdapter(listOf(),trefleDAO)
        plants.adapter = medicalPlantsAdapter
      //  medicalPlantsAdapter.updatePlants(listOfPlants)


        val novaBiljkaBtn = findViewById<Button>(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this,NovaBiljkaActivity::class.java)
            intent.putExtra("allPlants",listOfPlants as Serializable)
            startActivity(intent)
        }

    }
}