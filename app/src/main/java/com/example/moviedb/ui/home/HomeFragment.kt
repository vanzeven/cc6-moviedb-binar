package com.example.moviedb.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.adapter.HomeAdapter
import com.example.moviedb.databinding.FragmentHomeBinding
import com.example.moviedb.model.GetPopular
import com.example.moviedb.model.GetPopularItem
import com.example.moviedb.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val spLogin = "spLogin"
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(spLogin, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val username = "Welcome, " + sharedPreferences.getString("username_key", null) + "!"
        binding.tvUser.text = username

        recyclerView = binding.rvMovie
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchAllData { movies : List<GetPopularItem> -> recyclerView.adapter = HomeAdapter(movies) }

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_profileFragment)
        }
    }




    private fun fetchAllData(callback: (List<GetPopularItem>) -> Unit) {

        ApiClient.instance.getPopular().enqueue(object : Callback<GetPopular> {
            override fun onResponse(
                call: Call<GetPopular>,
                response: Response<GetPopular>
            ) {
                binding.pb.visibility = View.GONE
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<GetPopular>, t: Throwable) {
                binding.pb.visibility = View.GONE
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }

}