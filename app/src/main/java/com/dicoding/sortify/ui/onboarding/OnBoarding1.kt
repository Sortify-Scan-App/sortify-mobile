package com.dicoding.sortify.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.sortify.R
import com.dicoding.sortify.databinding.FragmentOnBoarding1Binding


class OnBoarding1 : Fragment() {
    private lateinit var binding: FragmentOnBoarding1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)
        return binding.root
    }
}
