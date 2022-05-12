package com.muhammadhusniabdillah.challengechapter5.ui.profile

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveApplication
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModel
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModelFactory
import com.muhammadhusniabdillah.challengechapter5.data.preferences.DataStorePreferences
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentUpdateProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateProfileFragment : Fragment() {

    private lateinit var pref: DataStorePreferences
    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin(), pref
        )
    }
    private lateinit var binding: FragmentUpdateProfileBinding
    private val args: UpdateProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val updateProfileBinding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        binding = updateProfileBinding
        return updateProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = DataStorePreferences(requireContext())

        setEditTextValue()

        binding.apply {
            btnUpdate.setOnClickListener {
                toProfilePage()
            }
        }
        onBackPressed()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle("Discard Changes?")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { _, _ ->
                    findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun toProfilePage() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = viewModel.userProfile(
                args.data2?.id!!,
                binding.etUpdateName.text.toString(),
                binding.etUpdateEmail.text.toString(),
                binding.etUpdatePassword.text.toString()
            )
            activity?.runOnUiThread {
                if (result != 0) {
                    Toast.makeText(requireContext(), "Changed successfully!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
                }
            }
        }
    }

    private fun setEditTextValue() {
        binding.apply {
            etUpdateName.setText(args.data2?.name)
            etUpdateEmail.setText(args.data2?.email)
            etUpdateEmail.isEnabled = false
        }
    }
}