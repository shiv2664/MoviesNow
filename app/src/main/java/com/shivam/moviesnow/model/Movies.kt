package com.shivam.moviesnow.model;

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Movies {
    @SerializedName("Movie List")
    @Expose
    var movieList: List<MovieList>? = null
}
