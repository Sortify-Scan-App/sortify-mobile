package com.dicoding.sortify.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.sortify.R
import com.dicoding.sortify.databinding.FragmentLoginBinding
import com.dicoding.sortify.ui.home.HomeActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.registerTextView.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvForgotPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.loginButton.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
