package com.example.bcsd_android_swapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bcsd_android_swapi.model.FilmsData
import com.example.bcsd_android_swapi.model.PeoplesData
import com.example.bcsd_android_swapi.model.PlanetsData

class MainViewModel : ViewModel() {
    val peoplesList: MutableLiveData<ArrayList<PeoplesData>> by lazy {
        MutableLiveData()
    }
    val planetsList: MutableLiveData<ArrayList<PlanetsData>> by lazy {
        MutableLiveData()
    }
    val filmsList: MutableLiveData<ArrayList<FilmsData>> by lazy {
        MutableLiveData()
    }

    val peoplesItem = ArrayList<PeoplesData>()
    val planetsItem = ArrayList<PlanetsData>()
    val filmsItem = ArrayList<FilmsData>()

    fun insertPeoplesList(
        name: String,
        birth: String,
        gender: String,
        height: String,
        mass: String,
        image: Int
    ) {
        peoplesItem.add(PeoplesData(name, birth, gender, height, mass, image))
        peoplesList.postValue(peoplesItem)
    }

    fun insertPlanetsList(
        name: String,
        climate: String,
        population: String,
        image: Int
    ) {
        planetsItem.add(PlanetsData(name, climate, population, image))
        planetsList.postValue(planetsItem)
    }

    fun insertFilmsList(
        title:String,
        director:String,
        releaseDate:String,
        image:Int
    ){
        filmsItem.add(FilmsData(title,director,releaseDate,image))
        filmsList.postValue(filmsItem)
    }
}