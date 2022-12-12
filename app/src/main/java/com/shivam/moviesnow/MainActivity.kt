package com.shivam.moviesnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivam.moviesnow.api.ApiClient
import com.shivam.moviesnow.api.ApiInterface
import com.shivam.moviesnow.databinding.ActivityMainBinding
import com.shivam.moviesnow.interfaces.IMainActivity
import com.shivam.moviesnow.model.MovieList
import com.shivam.moviesnow.model.Movies
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var binding:ActivityMainBinding
    private lateinit var mViewModel:MainActivityViewModel
    private var refresh: MenuItem? = null

    var isScrolling = false
    var currentItems = 0
    var totalItems:Int = 0
    var scrollOutItems:Int = 0
    var scrollOutItemsDOWN:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)



        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MainActivityViewModel::class.java]


        mViewModel.loadJSON1()

        if(mViewModel.checkForInternet(this)){
            mViewModel.getMovieListOnline().observe(this) {
                binding.dataList = it
            }
        }else{
            mViewModel.getMoviesList()?.observe(this) {
                binding.dataList = it
            }
        }


        //On Scroll Listener for loading new data
//        binding.moviesRecyclerView.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                    Log.d("MyTag","is scrolling "+isScrolling)
//                }
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                currentItems = moviesRecyclerView.layoutManager?.childCount!!
//                totalItems = moviesRecyclerView.layoutManager?.itemCount!!
//                scrollOutItems = (moviesRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                scrollOutItemsDOWN = (moviesRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//
//                if (isScrolling &&currentItems + scrollOutItems == totalItems) {
//                    isScrolling = false
//                    mViewModel.loadNewData()
//                }
//
//            }
//        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_mainactivity, menu)
        refresh = menu.findItem(R.id.action_refresh)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_refresh) {
            mViewModel.deleteAll()

        }
        return super.onOptionsItemSelected(item)
    }

}

