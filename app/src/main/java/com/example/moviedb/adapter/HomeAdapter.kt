package com.example.moviedb.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.databinding.MovieItemBinding
import com.example.moviedb.model.GetPopularItem

class HomeAdapter(private val movies: List<GetPopularItem>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(private var binding: MovieItemBinding, val movies: List<GetPopularItem>) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageBase = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie: GetPopularItem){
            val titleAndYear = movie.title + " (" + movie.releaseDate?.take(4) +  ")"
            binding.tvTitle.text = titleAndYear
            binding.tvRating.text = movie.voteAverage.toString()
            Glide.with(itemView.context).load(imageBase + movie.posterPath).into(binding.ivPoster)

            binding.rvMovie.setOnClickListener{
                val movieDetails = Bundle()
                movie.id?.let { it1 -> movieDetails.putInt("ID", it1) }
                it.findNavController().navigate(R.id.action_noteFragment_to_movieDetailFragment, movieDetails)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding, movies)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindMovie(movies.get(position))
    }
}