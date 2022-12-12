package com.shivam.moviesnow

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shivam.moviesnow.api.ApiClient
import com.shivam.moviesnow.api.ApiInterface
import com.shivam.moviesnow.model.MovieList
import com.shivam.moviesnow.model.Movies
import com.shivam.moviesnow.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val liveDataMovieList: MutableLiveData<MutableList<MovieList>> = MutableLiveData()
    private var movieList: MutableList<MovieList> = ArrayList<MovieList>()

    private var pagedListLiveData: LiveData<PagedList<MovieList>>? = null

    private val localDatabase: AppDatabase = AppDatabase.getInstance(application)!!

    @JvmName("getMoviesList")
    fun getMoviesList(): LiveData<List<MovieList>>? {
        return localDatabase.appDao()?.getMoviesList()
    }

    @JvmName("getMovieListOnline")
    fun getMovieListOnline(): MutableLiveData<MutableList<MovieList>> {
        return liveDataMovieList
    }

    fun deleteAll() {
        liveDataMovieList.value=movieList
        viewModelScope.launch(Dispatchers.IO) {
            localDatabase.appDao()?.deleteMovieList()
            loadJSON2()
        }

    }

//for paging
//    fun MoviesViewModel() {
//        pagedListLiveData = LivePagedListBuilder<Any?,Any?>(localDatabase.appDao()?.getAllMovies(), 10).build()
//    }

    fun loadJSON1() {
        val apiInterface: ApiInterface? = ApiClient.apiClient?.create(ApiInterface::class.java)
        val call: Call<Movies?>? = apiInterface?.getMovies("movies")
        call!!.enqueue(object : Callback<Movies?> {
            override fun onResponse(call: Call<Movies?>, response: Response<Movies?>) {
                if (response.isSuccessful && response.body()?.movieList != null) {
                    Log.d("MyTag", "Successful1"+response.body()?.movieList.toString())

                    viewModelScope.launch(Dispatchers.IO) {
                        liveDataMovieList.postValue(response.body()!!.movieList?.toMutableList()!!)
                        response.body()!!.movieList?.let {
                            localDatabase.appDao()?.insertMovies(it.toMutableList())
                        }
                    }
                } else {
                    val errorCode: String = when (response.code()) {
                        404 -> "404 not found"
                        500 -> "500 server broken"
                        else -> "unknown error"
                    }
                    Log.d("MyTag", errorCode)
                }
            }

            override fun onFailure(call: Call<Movies?>, t: Throwable) {

            }
        })
    }


    private fun loadJSON2() {
        val apiInterface: ApiInterface? = ApiClient.apiClient?.create(ApiInterface::class.java)
        val call: Call<Movies?>? = apiInterface?.getMovies2("movies")
        call!!.enqueue(object : Callback<Movies?> {
            override fun onResponse(call: Call<Movies?>, response: Response<Movies?>) {
                if (response.isSuccessful && response.body()?.movieList != null) {

                    Log.d("MyTag","Successful2"+response.body()?.movieList.toString())

                    viewModelScope.launch(Dispatchers.IO) {
                        liveDataMovieList.postValue(response.body()!!.movieList?.toMutableList()!!)
                        response.body()!!.movieList?.let {
                            localDatabase.appDao()?.insertMovies(it.toMutableList())
                        }
                    }
                } else {
                    val errorCode: String = when (response.code()) {
                        404 -> "404 not found"
                        500 -> "500 server broken"
                        else -> "unknown error"
                    }
                    Log.d("MyTag", errorCode)
                }
            }

            override fun onFailure(call: Call<Movies?>, t: Throwable) {

            }
        })
    }

    fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}

