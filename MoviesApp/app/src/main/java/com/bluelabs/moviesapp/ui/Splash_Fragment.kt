package com.bluelabs.moviesapp.ui

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bluelabs.moviesapp.R
import com.bluelabs.moviesapp.databinding.SplashFragmentBinding
import com.bluelabs.moviesapp.utils.InternetUtils

class Splash_Fragment : Fragment() {

    private lateinit var binding: SplashFragmentBinding
    private val internetUtils = InternetUtils()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashFragmentBinding.inflate(inflater, container, false)

        val sharedPref = this.requireActivity()
            .getSharedPreferences("Data", Context.MODE_PRIVATE)

        val datatopRatedMovies = sharedPref.getString("topRatedMovies", null)
        val datanowPlayingMovies = sharedPref.getString("nowPlayingMovies", null)
        val datapopularMovies = sharedPref.getString("popularMovies", null)

        binding.lottieanimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                try {
                    if (internetUtils.isNetworkAvailable(requireActivity())) {
                        findNavController().navigate(R.id.action_splash_to_home)
                    } else {
                        if (datatopRatedMovies != null && datanowPlayingMovies != null && datapopularMovies != null) {
                            findNavController().navigate(R.id.action_splash_to_home)
                        } else {
                            binding.textViewProgressBar.text =
                                "Conectate a internet y vuelve para ver tus peliculas favoritas"
                            binding.lottieanimation.setAnimation(R.raw.nointernet)
                            binding.lottieanimation.loop(true)
                            binding.lottieanimation.playAnimation()
                            binding.ProgressBar.visibility = View.INVISIBLE
                        }
                    }
                } catch (ex: Exception) {
                    ex.toString()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
        return binding.root
    }
}