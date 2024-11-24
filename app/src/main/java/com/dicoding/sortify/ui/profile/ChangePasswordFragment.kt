package com.dicoding.sortify.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.sortify.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tambahkan logika interaksi UI di sini
        binding.apply {
            // Contoh: binding.tvTitle.text = "Ganti Kata Sandi"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
