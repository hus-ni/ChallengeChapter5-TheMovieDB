package com.muhammadhusniabdillah.challengechapter5.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Constant
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Helper
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPref: Helper

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

        binding.apply {
            tvWelcome.text = getString(R.string.welcome_text,sharedPref.getName(Constant.RECENT_USER))
            btnProfile.setOnClickListener {
                toProfile()
            }
        }
    }

    private fun toProfile() {
        findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
    }

}