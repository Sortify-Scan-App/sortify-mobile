package com.dicoding.sortify.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dicoding.sortify.R
import com.dicoding.sortify.databinding.ActivityHomeBinding
import com.dicoding.sortify.ui.maps.MapsFragment
import com.dicoding.sortify.ui.history.HistoryFragment
import com.dicoding.sortify.ui.profile.ProfileFragment
import com.dicoding.sortify.ui.scan.ScanFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.framecontainer, HomeFragment())
                .commit()
        }

        binding.fabScan.setOnClickListener {
            binding.bottomnavigationbar.selectedItemId = binding.bottomnavigationbar.menu.getItem(2).itemId
            supportFragmentManager.beginTransaction()
                .replace(R.id.framecontainer, ScanFragment())
                .commit()
        }
    }

    private fun setupBottomNavigation() {
        binding.fabScan.imageTintList = null
        with(binding.bottomnavigationbar) {
            background = null
            itemIconTintList = null
            menu.getItem(2).isEnabled = false

            setOnItemSelectedListener { item ->
                val fragment: Fragment = when (item.itemId) {
                    R.id.mHome -> HomeFragment()
                    R.id.mSearch -> MapsFragment()
                    R.id.mHistory -> HistoryFragment()
                    R.id.mProfile -> ProfileFragment()
                    else -> return@setOnItemSelectedListener false
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.framecontainer, fragment)
                    .addToBackStack(null)
                    .commit()
                true
            }
        }
    }
}