package com.muhammadhusniabdillah.challengechapter5.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = detailsBinding
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImages(args.details.posterPath, args.details.backdropPath)

        binding.apply {
            movieTitle.text = args.details.title
            movieOverview.text = args.details.overview
            movieRating.rating = args.details.rating / 2
            movieReleaseDate.text = args.details.releaseDate
        }
    }

    fun setImages(posterPath: String, backdropPath: String) {
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w342$posterPath")
            .transform(CenterCrop())
            .into(binding.moviePoster)

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w1280$backdropPath")
            .transform(CenterCrop())
            .into(binding.movieBackdrop)
    }
}