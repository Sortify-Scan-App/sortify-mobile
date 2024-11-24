package com.dicoding.sortify.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.sortify.R
import com.dicoding.sortify.databinding.FragmentProfileBinding
import com.dicoding.sortify.ui.about.AboutActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.layoutAbout.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
