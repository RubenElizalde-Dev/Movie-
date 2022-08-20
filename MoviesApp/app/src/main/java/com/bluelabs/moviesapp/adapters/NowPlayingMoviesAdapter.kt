package com.bluelabs.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bluelabs.moviesapp.R
import com.bluelabs.moviesapp.domain.model.Resultss
import com.squareup.picasso.Picasso

class NowPlayingMoviesAdapter(private val nowplayingmovies: List<Resultss>) :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.NowPlayingMoviesAdapterViewHolder>() {

    var onItemClick: ((Resultss) -> Unit)? = null

    class NowPlayingMoviesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NowPlayingMovieImage: ImageView = itemView.findViewById(R.id.NowPlayingMovieImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingMoviesAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.nowplayingmoviesrow, parent, false)
        return NowPlayingMoviesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingMoviesAdapterViewHolder, position: Int) {
        val movie = nowplayingmovies[position]
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200/" + movie.posterPath)
            .into(holder.NowPlayingMovieImage);
        holder.NowPlayingMovieImage.setOnClickListener {
            onItemClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int {
        return nowplayingmovies.size
    }
}