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
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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
    private lateinit var pretragaET: EditText
    private lateinit var bojaSPIN: Spinner
    private lateinit var brzaPretraga: Button
    private lateinit var medicalPlantsAdapter: MedicalPlantsListAdapter
    private lateinit var cookingPlantsAdapter: CookingPlantsListAdapter
    private lateinit var botanicPlantsAdapter: BotanicPlantsListAdapter
    private lateinit var similarPlants: List <Biljka>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       // val trefleDAO = TrefleDAO(/*RetrofitClient.retrofit,*/this)


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

        pretragaET = findViewById<EditText>(R.id.pretragaET)
        pretragaET.visibility = View.INVISIBLE
        bojaSPIN = findViewById<Spinner>(R.id.bojaSPIN)
        bojaSPIN.visibility = View.INVISIBLE
        brzaPretraga = findViewById<Button>(R.id.brzaPretraga)
        brzaPretraga.visibility = View.INVISIBLE


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
                        pretragaET.visibility = View.INVISIBLE
                        bojaSPIN.visibility = View.INVISIBLE
                        brzaPretraga.visibility = View.INVISIBLE
                        val constraintLayout = findViewById<ConstraintLayout>(R.id.main)
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(constraintLayout)

                        constraintSet.connect(R.id.biljkeRV, ConstraintSet.TOP, R.id.resetBtn, ConstraintSet.BOTTOM)

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
                        pretragaET.visibility = View.INVISIBLE
                        bojaSPIN.visibility = View.INVISIBLE
                        brzaPretraga.visibility = View.INVISIBLE
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
                        pretragaET.visibility = View.VISIBLE
                        bojaSPIN.visibility = View.VISIBLE
                        brzaPretraga.visibility = View.VISIBLE
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

    /*    brzaPretraga.setOnClickListener {
            val query = pretragaET.text.toString()
            val selectedItem = bojaSPIN.selectedItem.toString()

            if (query.isNotEmpty() && selectedItem != null){
                CoroutineScope(Dispatchers.Main).launch {
                    val plants = trefleDAO.getPlantsWithFlowerColor(selectedItem, query)

                    botanicPlantsAdapter.updatePlants(plants, quickSearchMode = true)
                }
            }

        }*/

        medicalPlantsAdapter = MedicalPlantsListAdapter(listOf())
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