package com.bluelabs.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bluelabs.moviesapp.R
import com.bluelabs.moviesapp.domain.model.Results
import com.bluelabs.moviesapp.domain.model.Resultsss
import com.squareup.picasso.Picasso

class TopRatedMoviesAdapter(private val topratedmovies: List<Results>) :
    RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesAdapterViewHolder>() {

    var onItemClick: ((Results) -> Unit)? = null

    class TopRatedMoviesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TopMovieImage: ImageView = itemView.findViewById(R.id.TopMovieImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedMoviesAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.topratedmoviesrow, parent, false)
        return TopRatedMoviesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopRatedMoviesAdapterViewHolder, position: Int) {
        val movie = topratedmovies[position]
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200/" + movie.posterPath)
            .into(holder.TopMovieImage);
        holder.TopMovieImage.setOnClickListener {
            onItemClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int {
        return topratedmovies.size
    }
}