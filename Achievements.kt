package com.example.birdtrail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class Achievements : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var collectionRef: DatabaseReference
    private lateinit var totalItemsTextView: TextView
    private lateinit var pieChart: PieChart
    private var totalItems: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.navigation_trophy)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, ExploreActivity::class.java))
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
                else -> false
            }
        }

        totalItemsTextView = findViewById(R.id.totalItemsTextView)
        pieChart = findViewById(R.id.pieChart)

        // Update the totalItemsTextView and pie chart with the initial value of totalItems
        updateTotalItems(totalItems)

        collectionRef = FirebaseDatabase.getInstance().getReference("Birds") //(Jansen Van Rensburg, 2024)
        collectionRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the count of entries in the database
                val count = dataSnapshot.childrenCount.toInt()
                // Update the totalItems value with the count
                updateTotalItems(count)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
                Toast.makeText(applicationContext, "Failed to read data from database.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTotalItems(count: Int) {
        // Limit the totalItems value to be within the range of 0 to 10
        totalItems = count.coerceIn(0, 10)

        // Update the totalItemsTextView with the updated value
        totalItemsTextView.text = "$totalItems out of 10"

        // Update the pie chart with the calculated progress
        setupPieChart(totalItems)
    }

    private fun setupPieChart(progress: Int) {
        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(progress.toFloat(), "Progress"))
        entries.add(PieEntry((10 - progress).toFloat(), "Remaining"))

        val dataSet = PieDataSet(entries, "Achievements Progress")
        dataSet.colors = listOf(0xFFFFA500.toInt(), 0xFFD3D3D3.toInt()) // Customize colors

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(true)
        pieChart.invalidate() // Refresh the chart
    }
}
