package com.muhammadhusniabdillah.challengechapter5.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveApplication
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModel
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModelFactory
import com.muhammadhusniabdillah.challengechapter5.data.network.ApiClient
import com.muhammadhusniabdillah.challengechapter5.data.network.Movies
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Constant
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Helper
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentHomeBinding
import com.muhammadhusniabdillah.challengechapter5.ui.home.recycler.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin()
        )
    }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPref: Helper

    private lateinit var popular: RecyclerView
    private lateinit var popularAdapter: RecyclerAdapter
    private lateinit var popularLayoutManager: LinearLayoutManager
    private var popularPage = 1

    private lateinit var topRated: RecyclerView
    private lateinit var topRatedAdapter: RecyclerAdapter
    private lateinit var topRatedLayoutManager: LinearLayoutManager
    private var topRatedPage = 1

    private lateinit var upcoming: RecyclerView
    private lateinit var upcomingAdapter: RecyclerAdapter
    private lateinit var upcomingLayoutManager: LinearLayoutManager
    private var upcomingPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = homeBinding
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())

        setWelcomeName()
        binding.apply {
            btnProfile.setOnClickListener { toProfile() }
        }

        popularMovies()
        topRatedMovies()
        upcomingMovies()

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()

        doubleBackToExit()
    }


    private fun setWelcomeName() {
        lifecycleScope.launch(Dispatchers.IO) {
            val name = viewModel.getUserName(sharedPref.getEmail(Constant.EMAIL_USER)!!)
            activity?.runOnUiThread {
                binding.tvWelcome.text = getString(R.string.welcome_text, name)
            }
        }
    }

    private fun doubleBackToExit() {
        var doubleBackPressed: Long = 0
        val toast = Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (doubleBackPressed + 2000 > System.currentTimeMillis()) {
                activity?.finish()
                toast.cancel()
            } else {
                toast.show()
            }
            doubleBackPressed = System.currentTimeMillis()
        }
    }

    private fun toProfile() {
        val actionToProfile = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        findNavController().navigate(actionToProfile)
    }

    // ------------------------------------------------ //
    // API API API API API API API API API API API API  //

    private fun popularMovies() {
        popular = binding.recyclerPopularMovies
        popularLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popular.layoutManager = popularLayoutManager
        popularAdapter = RecyclerAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popular.adapter = popularAdapter
    }

    private fun upcomingMovies() {
        topRated = binding.recyclerTopRatedMovies
        topRatedLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRated.layoutManager = topRatedLayoutManager
        topRatedAdapter = RecyclerAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        topRated.adapter = topRatedAdapter
    }

    private fun topRatedMovies() {
        upcoming = binding.recyclerUpcomingMovies
        upcomingLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        upcoming.layoutManager = upcomingLayoutManager
        upcomingAdapter = RecyclerAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        upcoming.adapter = upcomingAdapter
    }

    private fun getPopularMovies() {
        ApiClient.getPopularMovies(
            popularPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movies>) {
        popularAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun attachPopularMoviesOnScrollListener() {
        popular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularLayoutManager.itemCount
                val visibleItemCount = popularLayoutManager.childCount
                val firstVisibleItem = popularLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popular.removeOnScrollListener(this)
                    popularPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun getTopRatedMovies() {
        ApiClient.getTopRatedMovies(
            topRatedPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun onTopRatedMoviesFetched(movies: List<Movies>) {
        topRatedAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRated.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedLayoutManager.itemCount
                val visibleItemCount = topRatedLayoutManager.childCount
                val firstVisibleItem = topRatedLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRated.removeOnScrollListener(this)
                    topRatedPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun getUpcomingMovies() {
        ApiClient.getUpcomingMovies(
            upcomingPage,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    private fun onUpcomingMoviesFetched(movies: List<Movies>) {
        upcomingAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcoming.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingLayoutManager.itemCount
                val visibleItemCount = upcomingLayoutManager.childCount
                val firstVisibleItem = upcomingLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcoming.removeOnScrollListener(this)
                    upcomingPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun showMovieDetails(movies: Movies) {
        val toDetailsPage = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movies)
        findNavController().navigate(toDetailsPage)
    }

    private fun onError() {
        Toast.makeText(requireContext(), getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT)
            .show()
    }
}