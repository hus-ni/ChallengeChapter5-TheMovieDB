package com.muhammadhusniabdillah.challengechapter5.ui.login

import android.os.Bundle
import android.os.Looper
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
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentLoginBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class LoginFragment : Fragment() {

    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin()
        )
    }
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = loginBinding
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            toHome()
        }

        binding.tvOrOptions.setOnClickListener {
            toRegister()
        }
    }

    private fun toRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun toHome() {
        // check login entries
        if (blankInputCheck()) {
            GlobalScope.launch {
                val check = viewModel.checkUserExists(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                if (check) {
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        //shared preferences editor here
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Invalid Email or Password!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "No empty field allowed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun blankInputCheck(): Boolean {
        return viewModel.isInputEmpty(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }
}