package com.example.rma24projekat_19153

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicalPlantsListAdapter(
    private var plants: List <Biljka>
): RecyclerView.Adapter<MedicalPlantsListAdapter.MedicalPlantsViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicalPlantsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicinski_layout,parent,false)
        return MedicalPlantsViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size
    override fun onBindViewHolder(holder: MedicalPlantsViewHolder, position: Int) {
        holder.plantNaziv.text = plants[position].naziv
        holder.plantImage.setImageResource(0)
        holder.plantUpozorenje.text = plants[position].medicinskoUpozorenje
      // Ovdje mozda prepravka
        val koristi = plants[position].medicinskeKoristi
        holder.plantKorist1.text = koristi.getOrNull(0)?.toString() ?: ""
        holder.plantKorist2.text = koristi.getOrNull(1)?.toString() ?: ""
        holder.plantKorist3.text = koristi.getOrNull(2)?.toString() ?: ""
    }

    fun updatePlants(plants: List<Biljka>){
        this.plants = plants
        notifyDataSetChanged()
    }

    inner class MedicalPlantsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val plantNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val plantUpozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val plantKorist1: TextView = itemView.findViewById(R.id.korist1Item)
        val plantKorist2: TextView = itemView.findViewById(R.id.korist2Item)
        val plantKorist3: TextView = itemView.findViewById(R.id.korist3Item)
    }
}
