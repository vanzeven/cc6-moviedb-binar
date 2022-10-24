package com.example.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.adapter.HomeAdapter
import com.example.moviedb.databinding.FragmentHomeBinding
import com.example.moviedb.model.GetPopular
import com.example.moviedb.model.GetPopularItem
import com.example.moviedb.di.ApiService
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var api : ApiService

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.rvMovie

        homeViewModel.getUsername().observe(viewLifecycleOwner) {
            binding.tvUser.text = "Welcome, " + it.toString() + "!"
        }
        binding.btnProfile.setOnClickListener{ findNavController().navigate(R.id.action_noteFragment_to_profileFragment) }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        getMovieData { movies : List<GetPopularItem> ->
            recyclerView.adapter = HomeAdapter(movies) }
    }

    private fun getMovieData(callback: (List<GetPopularItem>) -> Unit){
        api.getPopular().enqueue(object : Callback<GetPopular> {
            override fun onResponse(call: Call<GetPopular>, response: Response<GetPopular>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<GetPopular>, t: Throwable) {

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}