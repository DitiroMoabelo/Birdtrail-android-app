package com.example.birdtrail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.birdtrail.databinding.ActivityExploreBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException

class ExploreActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityExploreBinding
    private lateinit var placesClient: PlacesClient
    private lateinit var bottomNavigationView: BottomNavigationView

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize the Places API client
        Places.initialize(applicationContext, getString(R.string.api_key))
        placesClient = Places.createClient(this)

        // Initialize map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Set up search button click listener
        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            searchLocation()
        }

        // Retrieve the saved distance and unit from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedDistanceValue = sharedPreferences.getString("distanceValue", "")
        val savedDistanceUnit = sharedPreferences.getString("distanceUnit", "")

        // Display the saved distance and unit as text in the TextView
        val distanceTextView = findViewById<TextView>(R.id.textViewDistance)
        distanceTextView.text = "Distance: $savedDistanceValue $savedDistanceUnit"

        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.navigation_gallery -> {
                    startActivity(Intent(applicationContext, Gallery::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.navigation_trophy -> {
                    startActivity(Intent(applicationContext, Achievements::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.navigation_settings -> {
                    startActivity(Intent(applicationContext, SettingsActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.navigation_add -> {
                    startActivity(Intent(applicationContext, AddSightings::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                else -> false // Handle other cases here
            }
        }



    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Customize map settings
        mMap.uiSettings.isZoomControlsEnabled = true

        // Set up map markers and current location
        setUpMap()
        // Add markers with different colors
        val WalterSisuluBotanicalGardens = LatLng(-26.086204, 27.832359)
        val JohannesburgBotanicalGardens = LatLng(-26.161656, 28.006872)
        val MelvilleKoppiesNatureReserve = LatLng(-26.168583, 28.000218)
        val KlipriviersbergNatureReserve = LatLng(-26.278044, 27.993306)
        val KloofendalNatureReserve = LatLng(-26.141646, 27.882294)
        val DeltaPark = LatLng(-26.131432, 28.004288)
        val RietfonteinNatureReserve = LatLng(-26.099839, 28.010378)
        val LonehillNatureReserve = LatLng(-26.001561, 28.029667)
        val ModderfonteinReserve = LatLng(-26.092158, 28.145555)
        val AlbertsFarmConservancy = LatLng(-26.155722, 27.972894)
        val GoldenHarvestPark = LatLng(-26.064558, 27.964129)
        val GilloolysFarm = LatLng(-26.168849, 28.123196)
        val MelroseWildlifeSanctuary = LatLng(-26.134342, 28.060118)
        val DiepslootNatureReserve = LatLng(-25.939426, 28.012056)
        val JamesAndEthelGrayPark = LatLng(-26.144481, 28.063431)



        //Colour for markers
        val blueIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)

        val markerOptions1 = MarkerOptions().position(WalterSisuluBotanicalGardens).title("Walter Sisulu Botanical Gardens").icon(blueIcon)
        val markerOptions2 = MarkerOptions().position(JohannesburgBotanicalGardens).title("Johannesburg Botanical Gardens").icon(blueIcon)
        val markerOptions3 = MarkerOptions().position(MelvilleKoppiesNatureReserve).title("Melville Koppies Nature Reserve").icon(blueIcon)
        val markerOptions4 = MarkerOptions().position(KlipriviersbergNatureReserve).title("Klipriviersberg Nature Reserve").icon(blueIcon)
        val markerOptions5 = MarkerOptions().position(KloofendalNatureReserve).title("Kloofendal Nature Reserve").icon(blueIcon)
        val markerOptions6 = MarkerOptions().position(DeltaPark).title("Delta Park").icon(blueIcon)
        val markerOptions7 = MarkerOptions().position(RietfonteinNatureReserve).title("Rietfontein Nature Reserve").icon(blueIcon)
        val markerOptions8 = MarkerOptions().position(LonehillNatureReserve).title("Lonehill Nature Reserve").icon(blueIcon)
        val markerOptions9 = MarkerOptions().position(ModderfonteinReserve).title("Modderfontein Reserve").icon(blueIcon)
        val markerOptions10 = MarkerOptions().position(AlbertsFarmConservancy).title("Alberts Farm Conservancy").icon(blueIcon)
        val markerOptions11 = MarkerOptions().position(GoldenHarvestPark).title("Golden Harvest Park").icon(blueIcon)
        val markerOptions12 = MarkerOptions().position(GilloolysFarm).title("Gillooly's Farm").icon(blueIcon)
        val markerOptions13 = MarkerOptions().position(MelroseWildlifeSanctuary).title("Melrose Wildlife Sanctuary").icon(blueIcon)
        val markerOptions14 = MarkerOptions().position(DiepslootNatureReserve).title("Diepsloot Nature Reserve").icon(blueIcon)
        val markerOptions15 = MarkerOptions().position(JamesAndEthelGrayPark).title("James and Ethel Gray Park").icon(blueIcon)


        // Add the markers to the map
        mMap?.addMarker(markerOptions1)
        mMap?.addMarker(markerOptions2)
        mMap?.addMarker(markerOptions3)
        mMap?.addMarker(markerOptions4)
        mMap?.addMarker(markerOptions5)
        mMap?.addMarker(markerOptions6)
        mMap?.addMarker(markerOptions7)
        mMap?.addMarker(markerOptions8)
        mMap?.addMarker(markerOptions9)
        mMap?.addMarker(markerOptions10)
        mMap?.addMarker(markerOptions11)
        mMap?.addMarker(markerOptions12)
        mMap?.addMarker(markerOptions13)
        mMap?.addMarker(markerOptions14)
        mMap?.addMarker(markerOptions15)

    }


    private fun setUpMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }

        // Enable location on the map
        mMap.isMyLocationEnabled = true

        // Clear old markers before adding a new one
        mMap.clear()

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            location?.let {
                val currentLatLong = LatLng(location.latitude, location.longitude)

                // Log the received location for debugging
                Log.d("Location", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")

                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            } ?: run {
                // Handle the case where location is null
                Log.e("Location", "Last known location is null")
            }
        }.addOnFailureListener { e ->
            // Handle any failure to get location
            Log.e("Location", "Error getting last known location: ${e.message}", e)
        }
    }


    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false

    private fun searchLocation() {
        val location = binding.searchBar.text.toString().trim()
        var addressList: List<Address>? = null

        if (location.isEmpty()) {
            showToast("Provide search details")
            return
        }

        val geoCoder = Geocoder(this)
        try {
            addressList = geoCoder.getFromLocationName(location, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (addressList != null && addressList.isNotEmpty()) {
            val address = addressList[0]
            val latLng = LatLng(address.latitude, address.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title(location))
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        } else {
            showToast("Location not found")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }




}
// Bibliography
// Ali, M. (2018, jul 16). Google Maps api key Android Studio. Retrieved from Youtube:
// https://www.youtube.com/watch?v=6fVhmtzwvfk&amp;list=PLxefhmF0pcPlGUW8tyyOJ8-
// uF7Nk2VpSj&amp;index=2
// Ali, M. (2018, sep 18). youtube. Retrieved from Android Nearby Places Tutorial 06 - Making 3 classes
// - Google Maps Nearby Places Tutorial:
// https://www.youtube.com/watch?v=0QzKquJ4j8Y&amp;list=PLxefhmF0pcPlGUW8tyyOJ8-
// uF7Nk2VpSj&amp;index=6
// freecodecamp.org. (2021, jan 26). Android Studio Tutorial - Build a GPS App. Retrieved from
// Youtube: https://www.youtube.com/watch?v=_xUcYfbtfsI
// Jansen Van Rensburg, A. (n.d.). app.box.com. Retrieved september 9, 2024, from open source
// Example:
// https://app.box.com/s/yhxrl0a10z9guas3g6em4akt3t0lfud9