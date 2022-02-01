package com.example.bcsd_android_swapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bcsd_android_swapi.R
import com.example.bcsd_android_swapi.model.PlanetsData

class PlanetsAdapter(
    var planetsList: MutableLiveData<ArrayList<PlanetsData>>
) : RecyclerView.Adapter<PlanetsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.planets_name)
        val climate = itemView.findViewById<TextView>(R.id.planets_climate)
        val population = itemView.findViewById<TextView>(R.id.planets_population)
        val image = itemView.findViewById<ImageView>(R.id.planets_image)
        fun bind(planetsData: PlanetsData) {
            name.text = planetsData.name
            climate.text = planetsData.climate
            population.text = planetsData.population
            Glide.with(itemView.context)
                .load(planetsData.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_planets_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(planetsList.value!![position])
    }

    override fun getItemCount(): Int {
        return planetsList.value!!.size
    }
}