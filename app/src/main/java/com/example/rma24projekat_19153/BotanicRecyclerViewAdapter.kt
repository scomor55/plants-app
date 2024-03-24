package com.example.rma24projekat_19153

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BotanicPlantsListAdapter(
    private var plants: List <Biljka>
): RecyclerView.Adapter<BotanicPlantsListAdapter.BotanicPlantsViewHolder>(){
    private lateinit var itemClickListener: BotanicPlantsListAdapter.PlantItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BotanicPlantsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanicki_layout,parent,false)
        return BotanicPlantsViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size
    override fun onBindViewHolder(holder: BotanicPlantsViewHolder, position: Int) {
        holder.bind(plants[position])
    }

    fun updatePlants(plants: List<Biljka>){
        this.plants = plants
        notifyDataSetChanged()
    }

    inner class BotanicPlantsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val plantNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val plantPorodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val plantKlimatskiTip: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val plantZemljisniTip: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        fun bind(plant: Biljka){
            plantNaziv.text = plant.naziv
            plantImage.setImageResource(R.drawable.biljka)
            plantPorodica.text = plant.porodica
            plantKlimatskiTip.text = plant.klimatskiTipovi.getOrNull(0).toString()
            plantZemljisniTip.text = plant.zemljisniTipovi.getOrNull(0).toString()


            itemView.setOnClickListener {
                itemClickListener.onPlantItemClick(plant)
            }
        }
    }

    interface PlantItemClickListener {
        fun onPlantItemClick(plant: Biljka)
    }

    fun setOnPlantItemClickListener(listener: PlantItemClickListener) {
        itemClickListener = listener
    }
}
