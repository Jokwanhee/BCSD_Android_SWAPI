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
import com.example.bcsd_android_swapi.model.FilmsData

class FilmsAdapter(
    var filmsList: MutableLiveData<ArrayList<FilmsData>>
) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.films_title)
        val director = itemView.findViewById<TextView>(R.id.films_director)
        val releaseDate = itemView.findViewById<TextView>(R.id.films_releaseDate)
        val image = itemView.findViewById<ImageView>(R.id.films_images)
        fun bind(filmsData: FilmsData) {
            title.text = filmsData.title.toString()
            director.text = filmsData.director.toString()
            releaseDate.text = filmsData.releaseDate.toString()
            Glide.with(itemView.context)
                .load(filmsData.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_films_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmsList.value!![position])
    }

    override fun getItemCount(): Int {
        return filmsList.value!!.size
    }
}