package com.example.bcsd_android_swapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.bcsd_android_swapi.databinding.ActivityMainBinding
import com.example.bcsd_android_swapi.model.PeoplesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val manager = supportFragmentManager
    private val peoplesFragment = PeoplesFragment()
    private val filmsFragment = FilmsFragment()
    private val planetsFragment = PlanetsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        createBottomNavigation()
    }

    private fun createBottomNavigation(){
        val navigation = binding.bottomNavigation

        manager
            .beginTransaction()
            .add(R.id.fragment_frame, filmsFragment)
            .add(R.id.fragment_frame, planetsFragment)
            .add(R.id.fragment_frame, peoplesFragment)
            .commit()

        navigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.navigation_people -> {
                    manager
                        .beginTransaction()
                        .show(peoplesFragment)
                        .hide(planetsFragment)
                        .hide(filmsFragment)
                        .commit()
                }
                R.id.navigation_planets -> {
                    manager
                        .beginTransaction()
                        .show(planetsFragment)
                        .hide(peoplesFragment)
                        .hide(filmsFragment)
                        .commit()
                }
                R.id.navigation_films -> {
                    manager
                        .beginTransaction()
                        .show(filmsFragment)
                        .hide(peoplesFragment)
                        .hide(planetsFragment)
                        .commit()
                }

            }
            true
        }
    }
}

