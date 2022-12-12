package com.shivam.moviesnow.api

import com.shivam.moviesnow.model.MovieList
import com.shivam.moviesnow.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("1.json")
    fun getMovies(@Query("1.json") keyword: String?): Call<Movies?>?

    @GET("2.json")
    fun getMovies2(@Query("2.json") keyword: String?): Call<Movies?>?
}
