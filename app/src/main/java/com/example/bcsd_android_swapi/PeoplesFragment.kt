package com.example.bcsd_android_swapi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcsd_android_swapi.adapter.PeoplesAdapter
import com.example.bcsd_android_swapi.databinding.FragmentPeoplesBinding
import com.example.bcsd_android_swapi.model.PeoplesData
import com.example.bcsd_android_swapi.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PeoplesFragment : Fragment() {
    private lateinit var binding: FragmentPeoplesBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: PeoplesAdapter
    var data = MutableLiveData<ArrayList<PeoplesData>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_peoples, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extraButton()
        enabledButton()

        binding.progressBar.visibility = View.GONE

        val listObserver = Observer<ArrayList<PeoplesData>> {
            data.value = it
            adapter = PeoplesAdapter(data)
            binding.peopleRecyclerView.adapter = adapter
            binding.peopleRecyclerView.layoutManager = LinearLayoutManager(view.context)
        }
        mainViewModel.peoplesList.observe(viewLifecycleOwner, listObserver)
    }

    private fun extraButton() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(UserService::class.java)
        binding.extraButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val id = binding.inputPeopleId.text.toString()
            service.getPeoplesPage(page = id)?.enqueue(object : Callback<PeoplesData> {
                override fun onResponse(call: Call<PeoplesData>, response: Response<PeoplesData>) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val result: PeoplesData? = response.body()
                        val name = result?.name.toString()
                        val birth = result?.birthYear.toString()
                        val gender = result?.gender.toString()
                        val height = result?.height.toString()
                        val mass = result?.mass.toString()
                        mainViewModel.insertPeoplesList(
                            name,
                            birth,
                            gender,
                            height,
                            mass,
                            image = R.drawable.ic_baseline_people_alt_24
                        )
                    }
                }

                override fun onFailure(call: Call<PeoplesData>, t: Throwable) {
                }
            })
        }
    }

    private fun enabledButton() {
        binding.inputPeopleId.addTextChangedListener(object : TextWatcher {
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