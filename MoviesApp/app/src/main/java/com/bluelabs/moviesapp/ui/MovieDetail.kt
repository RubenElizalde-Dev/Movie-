package com.bluelabs.moviesapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bluelabs.moviesapp.databinding.FragmentMovieDetailBinding
import com.bluelabs.moviesapp.domain.api.ApiInterface
import com.bluelabs.moviesapp.domain.api.ApiUtilities
import com.bluelabs.moviesapp.domain.model.MovieDetail
import com.bluelabs.moviesapp.utils.InternetUtils
import com.bluelabs.moviesapp.viewmodel.MovieDetailViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MovieDetail : Fragment() {

    private val apiInterface: ApiInterface =
        ApiUtilities.getInstace().create(ApiInterface::class.java)
    private val movieDetailViewModel = MovieDetailViewModel(apiInterface)
    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailArgs by navArgs()
    private val gson = Gson()
    private val internetUtils = InternetUtils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)


        val sharedPref = this.requireActivity()
            .getSharedPreferences("Data", Context.MODE_PRIVATE)

        viewLifecycleOwner.lifecycleScope.launch {

            val dataMovieDetail = sharedPref.getString(args.movieID, null)
            if (dataMovieDetail != null) {
                movieDetailViewModel.movieDetail.postValue(
                    gson.fromJson(
                        dataMovieDetail,
                        MovieDetail::class.java
                    )
                )
            }

            if (internetUtils.isNetworkAvailable(requireActivity())) {
                movieDetailViewModel.getMovieDetailAPI(args.movieID);
            } else {
                if (dataMovieDetail == null) {
                    Toast.makeText(
                        context,
                        "Conectate a internet y vuelve para ver tus peliculas favoritas",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner) { detail ->

            with(sharedPref.edit()) {
                putString(args.movieID, gson.toJson(detail))
                apply()
            }

            Picasso.get()
                .load("https://image.tmdb.org/t/p/w200/" + detail.posterPath)
                .into(binding.detailImage);
            binding.detailName.text = detail.originalTitle
            binding.detailDate.text = detail.releaseDate
            binding.detailRate.text = detail.voteAverage!!.toInt().toString() + "/10"
            binding.detailTime.text = detail.runtime.toString() + " min"
            binding.detailResume.text = detail.overview
            var generos = ""
            for (lenguage in detail.spokenLanguages) {
                generos += lenguage.englishName + ","
            }
            binding.detailGenre.text = generos.dropLast(1)
            var prod = ""
            for (companie in detail.productionCompanies) {
                prod += companie.name + ","
            }
            binding.detailProduction.text = prod.dropLast(1)
        }

        return binding.root
    }
}