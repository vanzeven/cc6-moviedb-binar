package com.example.moviedb.di

import com.example.moviedb.model.GetPopular
import com.example.moviedb.model.GetPopularItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=6cf7c98d8b65a3bc61f70dd2f72accf2")
    fun getPopular(): Call<GetPopular>

    @GET("movie/{movie_id}?api_key=6cf7c98d8b65a3bc61f70dd2f72accf2")
    fun getDetail(@Path("movie_id") id: Int?): Call<GetPopularItem>
}