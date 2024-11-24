package com.dicoding.sortify.ui.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.dicoding.sortify.R
import com.dicoding.sortify.adapter.ViewPagerAdapter
import com.dicoding.sortify.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(
            OnBoarding1(),
            OnBoarding2(),
            OnBoarding3()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < fragmentList.size - 1) {
                binding.viewPager.setCurrentItem(currentItem + 1, true)
            } else {
                setOnBoardingFinished()
                findNavController().navigate(R.id.action_mainOnBoardingFragment_to_loginFragment)
            }
        }
        binding.tvSkip.setOnClickListener{
            findNavController().navigate(R.id.action_mainOnBoardingFragment_to_loginFragment)
            setOnBoardingFinished()
        }

        return binding.root
    }
    private fun setOnBoardingFinished() {
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("finished", true)
        editor.apply()
    }
}

