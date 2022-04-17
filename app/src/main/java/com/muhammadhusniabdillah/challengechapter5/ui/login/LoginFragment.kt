package com.muhammadhusniabdillah.challengechapter5.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.challengechapter5.R
import com.muhammadhusniabdillah.challengechapter5.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

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

        binding?.apply {
            loginFragment = this@LoginFragment
        }
    }

    fun toRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    fun toHome() {
        // check login entries
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}