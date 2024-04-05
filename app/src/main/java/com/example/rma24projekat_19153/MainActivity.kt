package com.example.rma24projekat_19153

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var plants: RecyclerView
    private lateinit var currentPlantsAdapter: MedicalPlantsListAdapter
    private var listOfPlants = getPlants()
    private lateinit var resetButton: Button
    private lateinit var medicalPlantsAdapter: MedicalPlantsListAdapter
    private lateinit var cookingPlantsAdapter: CookingPlantsListAdapter
    private lateinit var botanicPlantsAdapter: BotanicPlantsListAdapter
    private lateinit var similarPlants: List <Biljka>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


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
                        medicalPlantsAdapter = MedicalPlantsListAdapter(listOf())
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
        medicalPlantsAdapter = MedicalPlantsListAdapter(listOf())
        plants.adapter = medicalPlantsAdapter
        medicalPlantsAdapter.updatePlants(listOfPlants)

    }
}