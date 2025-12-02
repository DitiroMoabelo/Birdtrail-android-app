package com.example.birdtrail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import com.google.firebase.database.*
import com.bumptech.glide.Glide

class Gallery : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var birdRecyclerView: RecyclerView
    private lateinit var birdArrayList: ArrayList<BirdData>
    private lateinit var adapter: BirdAdapter
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        birdRecyclerView = findViewById(R.id.recycler_item)
        birdRecyclerView.layoutManager = LinearLayoutManager(this)
        birdRecyclerView.setHasFixedSize(true)

        birdArrayList = arrayListOf()
        adapter = BirdAdapter(birdArrayList)
        birdRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : BirdAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedBird = birdArrayList[position]

                val dialogBuilder = AlertDialog.Builder(this@Gallery)
                dialogBuilder.setTitle("Bird Details")
                    .setMessage("Name: ${selectedBird.birdName}\nLocation: ${selectedBird.birdLocation}\nDescription: ${selectedBird.birdDescription}")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })

        dbRef = FirebaseDatabase.getInstance().getReference("Birds")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                birdArrayList.clear()
                for (birdSnapshot in snapshot.children) {
                    // Ensure you're using the correct data model
                    val bird = birdSnapshot.getValue(BirdData::class.java)
                    bird?.let {
                        // Check if all fields are populated correctly
                        Log.d("Gallery", "Retrieved bird: ${it.birdName}, ${it.birdLocation}, ${it.birdDescription}, ${it.birdImage}")
                        birdArrayList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Gallery", "Failed to retrieve bird data: ${error.message}")
            }
        })

        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.navigation_gallery)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.navigation_gallery -> true
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