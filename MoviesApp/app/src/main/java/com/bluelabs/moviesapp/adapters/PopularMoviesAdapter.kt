package com.bluelabs.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bluelabs.moviesapp.R
import com.bluelabs.moviesapp.domain.model.Resultss
import com.bluelabs.moviesapp.domain.model.Resultsss
import com.squareup.picasso.Picasso

class PopularMoviesAdapter(private val poupularMovies: List<Resultsss>) :
    RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesAdapterViewHolder>() {

    var onItemClick: ((Resultsss) -> Unit)? = null

    class PopularMoviesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val PopularMovieImage: ImageView = itemView.findViewById(R.id.PopularMovieImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMoviesAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.popularmoviesrow, parent, false)
        return PopularMoviesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMoviesAdapterViewHolder, position: Int) {
        val movie = poupularMovies[position]
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200/" + movie.posterPath)
            .into(holder.PopularMovieImage);
        holder.PopularMovieImage.setOnClickListener {
            onItemClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int {
        return poupularMovies.size
    }

}