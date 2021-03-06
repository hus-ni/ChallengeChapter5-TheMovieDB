package com.muhammadhusniabdillah.challengechapter5.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveApplication
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModel
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModelFactory
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Constant
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Helper
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin()
        )
    }
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = profileBinding
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextViews()
        Helper.init(requireContext())
        sharedPref = Helper

        binding.apply {
            btnToUpdate.setOnClickListener { toUpdateWithData() }
            btnLogout.setOnClickListener { loggingOut() }
        }
    }

    private fun toUpdateWithData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data2 = viewModel.getUserProfile(sharedPref.getEmail(Constant.EMAIL_USER))
            activity?.runOnUiThread {
                val actionToProfileUpdate =
                    ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(data2)
                findNavController().navigate(actionToProfileUpdate)
            }
        }
    }

    private fun setTextViews() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data2 = viewModel.getUserProfile(sharedPref.getEmail(Constant.EMAIL_USER))
            activity?.runOnUiThread {
                binding.apply {
                    tvName.text = data2.name
                    tvEmail.text = data2.email
                    tvPassword.text = data2.password
                }
            }
        }
    }

    private fun loggingOut() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout?")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { _, _ ->
                sharedPref.clear()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}