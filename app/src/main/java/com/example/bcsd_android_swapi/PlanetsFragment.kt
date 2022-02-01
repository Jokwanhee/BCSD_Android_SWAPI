package com.example.bcsd_android_swapi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcsd_android_swapi.adapter.PlanetsAdapter
import com.example.bcsd_android_swapi.databinding.FragmentPlanetsBinding
import com.example.bcsd_android_swapi.model.PeoplesData
import com.example.bcsd_android_swapi.model.PlanetsData
import com.example.bcsd_android_swapi.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanetsFragment : Fragment() {
    private lateinit var binding: FragmentPlanetsBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: PlanetsAdapter
    var data = MutableLiveData<ArrayList<PlanetsData>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_planets, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enabledButton()
        extraButton()
        binding.progressBar.visibility = View.GONE

        val listObserver = Observer<ArrayList<PlanetsData>> {
            data.value = it
            adapter = PlanetsAdapter(data)
            binding.planetsRecyclerView.adapter = adapter
            binding.planetsRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        }

        mainViewModel.planetsList.observe(viewLifecycleOwner, listObserver)

    }

    private fun extraButton() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(UserService::class.java)
        binding.extraButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val id = binding.inputPlanetsId.text.toString()
            service.getPlanetsPage(id)?.enqueue(object : Callback<PlanetsData> {
                override fun onResponse(call: Call<PlanetsData>, response: Response<PlanetsData>) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val result: PlanetsData? = response.body()
                        val name = result?.name.toString()
                        val climate = result?.climate.toString()
                        val population = result?.population.toString()
                        mainViewModel.insertPlanetsList(
                            name,
                            climate,
                            population,
                            R.drawable.ic_baseline_cloud_circle_24
                        )
                    }
                }

                override fun onFailure(call: Call<PlanetsData>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun enabledButton() {
        binding.inputPlanetsId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length > 0) {
                    binding.extraButton.isEnabled = true
                } else {
                    binding.extraButton.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}