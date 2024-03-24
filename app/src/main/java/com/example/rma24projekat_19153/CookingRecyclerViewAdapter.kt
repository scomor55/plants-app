package com.example.rma24projekat_19153

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CookingPlantsListAdapter(
    private var plants: List <Biljka>
): RecyclerView.Adapter<CookingPlantsListAdapter.CookingPlantsViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CookingPlantsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kuharski_layout,parent,false)
        return CookingPlantsViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size
    override fun onBindViewHolder(holder: CookingPlantsViewHolder, position: Int) {
        holder.plantNaziv.text = plants[position].naziv
        holder.plantImage.setImageResource(0)
        holder.plantOkus.text = plants[position].profilOkusa.opis
        // Ovdje mozda prepravka
        val jela = plants[position].jela
        holder.plantJelo1.text = jela.getOrNull(0)?.toString() ?: ""
        holder.plantJelo2.text = jela.getOrNull(1)?.toString() ?: ""
        holder.plantJelo3.text = jela.getOrNull(2)?.toString() ?: ""
    }

    fun updatePlants(plants: List<Biljka>){
        this.plants = plants
        notifyDataSetChanged()
    }

    inner class CookingPlantsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val plantNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val plantOkus: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val plantJelo1: TextView = itemView.findViewById(R.id.jelo1Item)
        val plantJelo2: TextView = itemView.findViewById(R.id.jelo2Item)
        val plantJelo3: TextView = itemView.findViewById(R.id.jelo3Item)
    }
}
