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
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BotanicPlantsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanicki_layout,parent,false)
        return BotanicPlantsViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size
    override fun onBindViewHolder(holder: BotanicPlantsViewHolder, position: Int) {
        holder.plantNaziv.text = plants[position].naziv
        holder.plantImage.setImageResource(0)
        holder.plantPorodica.text = plants[position].porodica
        // Ovdje mozda prepravka
        holder.plantKlimatskiTip.text = plants[position].klimatskiTipovi.getOrNull(0).toString()
        holder.plantZemljisniTip.text = plants[position].zemljisniTipovi.getOrNull(0).toString()

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
    }
}
