package com.example.birdtrail



import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val aviaryCard = findViewById<CardView>(R.id.myAviary)
        val sightingsCard = findViewById<CardView>(R.id.sightings_card)
        val settingsCard = findViewById<CardView>(R.id.settings_card)
        val exploreCard = findViewById<CardView>(R.id.explore_card)
        val speciesCard = findViewById<CardView>(R.id.species_card)

        aviaryCard.setOnClickListener {
            startActivity(Intent(this@HomeActivity, Gallery::class.java))
        }
        exploreCard.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ExploreActivity::class.java))
        }
        sightingsCard.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AddSightings::class.java))
        }

        settingsCard.setOnClickListener {
            startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
        }

        speciesCard.setOnClickListener {
            startActivity(Intent(this@HomeActivity, Achievements::class.java))
        }
    }
}
