package com.shivam.moviesnow.recyclerview_adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.shivam.moviesnow.BR
import com.shivam.moviesnow.databinding.ItemMovieBinding
import com.shivam.moviesnow.interfaces.IMainActivity
import com.shivam.moviesnow.model.MovieList
import com.shivam.moviesnow.room.AppDatabase
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesAdapter(private val context: Context, private var movieList: MutableList<MovieList>) :
    RecyclerView.Adapter<MoviesAdapter.BindingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {

        val rooView: ViewDataBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(rooView)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {

        val movieCurrent = movieList[position]
        holder.itemBinding.setVariable(BR.movieItem, movieCurrent)
        holder.itemBinding.setVariable(BR.listener, context as IMainActivity)
        holder.itemBinding.executePendingBindings()

        // can't use glide in xml so setting image here
        var thumbnailImage: String? = null
        CoroutineScope(Dispatchers.IO).launch {

            thumbnailImage = movieList[position].MoviePoster
            withContext(Dispatchers.Main) {
                if (thumbnailImage != null) {
                    Glide.with(context)
                        .load(thumbnailImage)
                        .into(holder.itemBinding.root.img)

                }

            }

        }

        Log.d("MyTag","Image is "+thumbnailImage.toString())


    }

    override fun getItemCount(): Int {
//        Log.d("MyTag","notification list size : "+notificationList.size)
        return movieList.size
    }

    fun updateData(newDataList: List<MovieList>) {
        val oldList = movieList
        val diffUtil: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MovieItemDiffCallback(
                oldList, newDataList
            )
        )
        movieList = newDataList.toMutableList()
        diffUtil.dispatchUpdatesTo(this)
    }

    class MovieItemDiffCallback(
        var oldMovieList: List<MovieList>,
        var newMovieList: List<MovieList>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldMovieList.size
        }

        override fun getNewListSize(): Int {
            return newMovieList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMovieList[oldItemPosition].Title == newMovieList[newItemPosition].Title)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMovieList[oldItemPosition] == newMovieList[newItemPosition])
        }
    }

    class BindingViewHolder(val itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {}

}