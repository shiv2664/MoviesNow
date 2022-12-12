package com.shivam.moviesnow.databindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.*
import com.shivam.moviesnow.model.MovieList
import com.shivam.moviesnow.recyclerview_adapters.MoviesAdapter


@BindingAdapter("bindmovierecyclerview")
fun bindMovieRecyclerView(view: RecyclerView, dataList: List<MovieList>?) {

    val linearLayoutManager = LinearLayoutManager(view.context)
    val layoutManager = view.layoutManager
    if (layoutManager == null) {
        view.layoutManager = linearLayoutManager
    }



//    view.itemAnimator = DefaultItemAnimator()
//    view.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))

    view.setHasFixedSize(true)

    var adapter: MoviesAdapter? = view.adapter as MoviesAdapter?

    if (adapter == null) {
        if (dataList != null) {
            adapter = MoviesAdapter(view.context, dataList.toMutableList())
            view.adapter = adapter
        }
    }

    if (dataList != null) {
        adapter?.updateData(dataList)
//        if (adapter != null) {
//            if (!view.canScrollVertically(-1)) {
//                // Its at bottom
//                view.scrollToPosition(0)
//            } else {
////                adapter.showToast()
//            }
//
//        }
    }


//    if (dataList != null) {
//        adapter?.updateData(dataList)
//    }

}
