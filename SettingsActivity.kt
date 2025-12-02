package com.example.birdtrail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var distanceEditText: EditText
    private lateinit var unitSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var exploreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        distanceEditText = findViewById(R.id.distanceEditText)
        unitSpinner = findViewById(R.id.spinnerUnit)
        saveButton = findViewById(R.id.saveButton)
        exploreButton = findViewById(R.id.exploreButton)

        // Load saved preferences if any
        loadSavedPreferences()

        // Set listener for the save button
        saveButton.setOnClickListener {
            saveDistance()
            Snackbar.make(it, "Distance saved successfully!", Snackbar.LENGTH_LONG).show() //(Jansen Van Rensburg, 2024)
        }

        // Set listener for the explore button to navigate to ExploreActivity
        exploreButton.setOnClickListener {
            val intent = Intent(this, ExploreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadSavedPreferences() {
        // Load saved distance and unit from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedDistanceValue = sharedPreferences.getString("distanceValue", "")
        val savedDistanceUnit = sharedPreferences.getString("distanceUnit", "")

        // Set the loaded values in the UI
        distanceEditText.setText(savedDistanceValue)
        // If your Spinner is based on an adapter, you may need to select the right item like this:
        val unitPosition = resources.getStringArray(R.array.units_array).indexOf(savedDistanceUnit)
        unitSpinner.setSelection(unitPosition)
    }

    private fun saveDistance() {
        // Get input from the user
        val distanceValue = distanceEditText.text.toString()
        val distanceUnit = unitSpinner.selectedItem.toString()

        // Save the input to SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("distanceValue", distanceValue)
        editor.putString("distanceUnit", distanceUnit)
        editor.apply()  // Save the changes

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
    }
}
