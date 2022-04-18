package com.muhammadhusniabdillah.challengechapter5.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveApplication
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModel
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModelFactory
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentRegisterBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class RegisterFragment : Fragment() {

    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin()
        )
    }
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding = registerBinding
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            toLogin()
        }
    }

    private fun toLogin() {
        if (blankInputCheck()) {
            if (passwordConfirmCheck()) {
                GlobalScope.launch {
                    viewModel.addUserProfile(
                        binding.etRegisterName.text.toString(),
                        binding.etRegisterEmail.text.toString(),
                        binding.etRegisterPassword.text.toString()
                    )
                }
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Register Succeed", Toast.LENGTH_LONG).show()
            } else {
                binding.tilRegisterPasswordConfirm.error =
                    getString(R.string.error_password_confirm)
            }
        } else {
            Toast.makeText(requireContext(), "No empty field allowed!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun passwordConfirmCheck(): Boolean {
        return binding.etRegisterPassword.text.toString() == binding.etRegisterPasswordConfirm.text.toString()
    }

    private fun blankInputCheck(): Boolean {
        return viewModel.isInputEmpty(
            binding.etRegisterName.text.toString(),
            binding.etRegisterEmail.text.toString(),
            binding.etRegisterPassword.text.toString(),
            binding.etRegisterPasswordConfirm.text.toString()
        )
    }
}