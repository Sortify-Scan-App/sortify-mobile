package com.dicoding.sortify.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dicoding.sortify.R
import com.dicoding.sortify.data.pref.UserPreference
import com.dicoding.sortify.ui.home.HomeActivity
import com.dicoding.sortify.ui.login.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.let { window ->
            window.statusBarColor = resources.getColor(android.R.color.white, null)
        }

        userPreference = UserPreference.getInstance(requireContext())
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                if (onBoardingIsFinished()) {
                    val user = userPreference.getSession().first()
                    if (user.isLogin) {
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_mainOnBoardingFragment)
                }
            }
        }, 3000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingIsFinished(): Boolean{
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }

}