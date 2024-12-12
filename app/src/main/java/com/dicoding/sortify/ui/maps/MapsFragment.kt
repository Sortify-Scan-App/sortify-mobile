package com.dicoding.sortify.ui.maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dicoding.sortify.R
import com.dicoding.sortify.data.local.BankSampahData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        for (bankSampah in BankSampahData.bankSampahList) {
            val location = LatLng(bankSampah.latitude, bankSampah.longitude)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(bankSampah.name)
                    .snippet(bankSampah.address)
            )
            marker?.tag = bankSampah
        }

        val firstLocation = LatLng(
            BankSampahData.bankSampahList[1].latitude,
            BankSampahData.bankSampahList[1].longitude
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 6f))
    }
}
