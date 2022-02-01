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
import com.example.bcsd_android_swapi.model.PeoplesData

class PeoplesAdapter(
    var peoplesList: MutableLiveData<ArrayList<PeoplesData>>
) : RecyclerView.Adapter<PeoplesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.peoples_name)
        val birth = itemView.findViewById<TextView>(R.id.peoples_birth)
        val gender = itemView.findViewById<TextView>(R.id.peoples_gender)
        val height = itemView.findViewById<TextView>(R.id.peoples_height)
        val mass = itemView.findViewById<TextView>(R.id.peoples_mass)
        val image = itemView.findViewById<ImageView>(R.id.peoples_image)
        fun bind(peoplesData: PeoplesData) {
            name.text = peoplesData.name
            birth.text = peoplesData.birthYear
            gender.text = peoplesData.gender
            height.text = peoplesData.height
            mass.text = peoplesData.mass
            Glide.with(itemView.context)
                .load(peoplesData.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_peoples_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(peoplesList.value!![position])
    }

    override fun getItemCount(): Int {
        return peoplesList.value!!.size
    }
}