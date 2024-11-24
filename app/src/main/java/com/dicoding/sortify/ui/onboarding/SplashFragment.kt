package com.dicoding.sortify.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dicoding.sortify.R

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.let { window ->
            window.statusBarColor = resources.getColor(android.R.color.white, null)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (onBoardingIsFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else{
                findNavController().navigate(R.id.action_splashFragment_to_mainOnBoardingFragment)
            }
        }, 3000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingIsFinished(): Boolean{
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }

}