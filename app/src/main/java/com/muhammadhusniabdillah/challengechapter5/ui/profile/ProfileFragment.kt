package com.muhammadhusniabdillah.challengechapter5.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveApplication
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModel
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModelFactory
import com.muhammadhusniabdillah.challengechapter5.data.preferences.DataStorePreferences
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class ProfileFragment : Fragment() {

    private lateinit var pref: DataStorePreferences
    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin(), pref
        )
    }
    private lateinit var binding: FragmentProfileBinding

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
        pref = DataStorePreferences(requireContext())

        loadImage()
        setTextViews()


        binding.apply {
            btnToUpdate.setOnClickListener { toUpdateWithData() }
            btnLogout.setOnClickListener { loggingOut() }
            profilePics.setOnClickListener { setProfilePicture() }
        }
    }

    private fun setProfilePicture() {
        ImagePicker.with(requireActivity())
            .crop()
            .saveDir(File(activity?.externalCacheDir, "ProfilePics"))
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent {
                profilePicsResult.launch(it)
            }

    }

    private val profilePicsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val result = it.resultCode
            val data = it.data
            when (result) {
                Activity.RESULT_OK -> {
                    val theUri = data?.data
                    lifecycleScope.launch(Dispatchers.IO) {
                        //saving pics to room
                        viewModel.updateProfilePics(theUri.toString(), pref.getEmail().first())
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    //nothing
                }
            }
        }

    private fun loadImage(theUri: Uri?) {
        theUri?.let {
            binding.profilePics.setImageURI(it)
        }
    }

    private fun loadImage() {
        viewModel.picture?.observe(viewLifecycleOwner) {
            loadImage(viewModel.picture?.value?.toUri())
        }
    }

    private fun toUpdateWithData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data2 = viewModel.getUserProfile(pref.getEmail().first())
            activity?.runOnUiThread {
                val actionToProfileUpdate =
                    ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(
                        data2
                    )
                findNavController().navigate(actionToProfileUpdate)
            }
        }
    }

    private fun setTextViews() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data2 = viewModel.getUserProfile(pref.getEmail().first())
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
                lifecycleScope.launch(Dispatchers.IO) {
                    pref.saveSession(DataStorePreferences.NOT_LOGGED_IN)
                }
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}