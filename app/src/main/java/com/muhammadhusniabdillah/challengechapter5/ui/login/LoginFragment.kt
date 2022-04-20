package com.muhammadhusniabdillah.challengechapter5.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveApplication
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModel
import com.muhammadhusniabdillah.challengechapter5.data.ChapterFiveViewModelFactory
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Constant
import com.muhammadhusniabdillah.challengechapter5.data.preferences.Helper
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private val viewModel: ChapterFiveViewModel by viewModels {
        ChapterFiveViewModelFactory(
            (activity?.application as ChapterFiveApplication).database.daoLogin()
        )
    }
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: Helper

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

        sharedPref = Helper(requireContext())
        binding.apply {
            imgLoginLogo.setImageResource(R.drawable.ic_login_logo_tmdb)
            btnLogin.setOnClickListener { toHome() }
            tvOrOptions.setOnClickListener { toRegister() }
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getLoginStatus(Constant.IS_LOGIN)) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun toHome() {
        // check login entries
        if (blankInputCheck()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val check = viewModel.checkUserExists(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                if (check) {
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        saveSession(binding.etEmail.text.toString(), check)
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

    private fun toRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun blankInputCheck(): Boolean {
        return viewModel.isInputEmpty(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    private fun saveSession(email: String, session: Boolean) {
        sharedPref.putEmail(Constant.EMAIL_USER, email)
        sharedPref.putLoginStatus(Constant.IS_LOGIN, session)
    }
}