package com.shivam.moviesnow.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shivam.moviesnow.model.MovieList

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieList: List<MovieList>)

    @Query("Select * From MovieList order by Year desc")
    fun getMoviesList(): LiveData<List<MovieList>>

    @Query("Select MoviePoster From MovieList where IMDBID= :IMDBID")
    fun getMoviePoster(IMDBID: String) :String

    @Query("DELETE FROM MovieList")
    fun deleteMovieList()

    //for getting pages data
    @Query("SELECT * FROM MovieList ORDER BY Year desc")
    fun getAllMovies(): DataSource.Factory<Int,MovieList>



}