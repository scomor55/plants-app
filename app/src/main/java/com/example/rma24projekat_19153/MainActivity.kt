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

/*import android.widget.Spinner*/

class MainActivity : AppCompatActivity() {
    private lateinit var plants: RecyclerView
    private lateinit var currentPlantsAdapter: MedicalPlantsListAdapter
    private var listOfPlants = getPlants()
    private lateinit var resetButton: Button
    private lateinit var medicalPlantsAdapter: MedicalPlantsListAdapter
    private lateinit var cookingPlantsAdapter: CookingPlantsListAdapter
    private lateinit var botanicPlantsAdapter: BotanicPlantsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }*/

        plants = findViewById(R.id.biljkeRV)
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        plants.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

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
                        medicalPlantsAdapter.updatePlants(listOfPlants)
                        medicalPlantsAdapter.setOnPlantItemClickListener(object: MedicalPlantsListAdapter.PlantItemClickListener {
                            override fun onPlantItemClick(plant: Biljka) {
                                val similarPlants = listOfPlants.filter {
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
                        cookingPlantsAdapter.updatePlants(listOfPlants)
                        cookingPlantsAdapter.setOnPlantItemClickListener(object : CookingPlantsListAdapter.PlantItemClickListener{
                            override fun onPlantItemClick(plant: Biljka) {
                                val similarPlants = listOfPlants.filter {
                                    it.jela.intersect(plant.jela)
                                        .isNotEmpty()
                                }
                                cookingPlantsAdapter.updatePlants(similarPlants)
                            }
                        })
                    }
                    "Botanički" ->{
                        botanicPlantsAdapter = BotanicPlantsListAdapter(listOf())
                        plants.adapter = botanicPlantsAdapter
                        botanicPlantsAdapter.updatePlants(listOfPlants)
                        botanicPlantsAdapter.setOnPlantItemClickListener(object : BotanicPlantsListAdapter.PlantItemClickListener{
                            override fun onPlantItemClick(plant: Biljka) {
                                val similarPlants = listOfPlants.filter {
                                    it.porodica == plant.porodica
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
            plants.adapter = medicalPlantsAdapter
            medicalPlantsAdapter.updatePlants(listOfPlants)
        }

        /*plants = findViewById(R.id.biljkeRV)
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        plants.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        */medicalPlantsAdapter = MedicalPlantsListAdapter(listOf())
        plants.adapter = medicalPlantsAdapter
        medicalPlantsAdapter.updatePlants(listOfPlants)

        /**/
        //End of spinner controller

    }
}