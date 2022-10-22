package com.example.moviedb.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import com.example.moviedb.model.GetPopularItem
import com.example.moviedb.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetail()
    }

    private fun getDetail() {
        val id = arguments?.getInt("ID")
        ApiClient.instance.getDetail(id).enqueue(object: Callback<GetPopularItem> {
            override fun onResponse(
                call: Call<GetPopularItem>,
                response: Response<GetPopularItem>
            ) {
                val body = response.body()
                val imageBase = "https://image.tmdb.org/t/p/w500/"

                val title: String = body?.title + " (" + body?.releaseDate?.take(4) +  ")"
                val rating = "Rating: " + body?.voteAverage.toString()
                val runtime = "Runtime: " + body?.runtime.toString() + " minutes"
                binding.tvTitle.text = title
                binding.tvRating.text = rating
                binding.tvRuntime.text = runtime
                binding.tvDesc.text = body?.overview
                Glide.with(requireContext()).load(imageBase + body?.posterPath).into(binding.ivPoster)
            }

            override fun onFailure(call: Call<GetPopularItem>, t: Throwable) {
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }
}