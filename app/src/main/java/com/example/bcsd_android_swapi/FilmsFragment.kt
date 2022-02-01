package com.example.bcsd_android_swapi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcsd_android_swapi.adapter.FilmsAdapter
import com.example.bcsd_android_swapi.databinding.FragmentFilmsBinding
import com.example.bcsd_android_swapi.model.FilmsData
import com.example.bcsd_android_swapi.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmsFragment : Fragment() {
    private lateinit var binding: FragmentFilmsBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: FilmsAdapter
    var data = MutableLiveData<ArrayList<FilmsData>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_films, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extraButton()
        enabledButton()

        binding.progressBar.visibility = View.GONE

        val listObserver = Observer<ArrayList<FilmsData>> {
            data.value = it
            adapter = FilmsAdapter(data)
            binding.peopleRecyclerView.adapter = adapter
            binding.peopleRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        mainViewModel.filmsList.observe(viewLifecycleOwner, listObserver)
    }

    private fun extraButton() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(UserService::class.java)
        binding.extraButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val id = binding.inputFilmsId.text.toString()
            service.getFilmsPage(id).enqueue(object : Callback<FilmsData> {
                override fun onResponse(call: Call<FilmsData>, response: Response<FilmsData>) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val result: FilmsData? = response.body()
                        val title = result?.title.toString()
                        val director = result?.director.toString()
                        val releaseDate = result?.releaseDate.toString()
                        mainViewModel.insertFilmsList(
                            title = title,
                            director = director,
                            releaseDate = releaseDate,
                            image = R.drawable.ic_baseline_movie_24
                        )
                    }
                }

                override fun onFailure(call: Call<FilmsData>, t: Throwable) {
                }
            })
        }
    }

    private fun enabledButton() {
        binding.inputFilmsId.addTextChangedListener(object : TextWatcher {
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