package com.example.birdtrail



import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.example.birdtrail.databinding.ActivitySightingsBinding
import java.text.SimpleDateFormat
import java.util.*

class AddSightings  : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var storage: FirebaseStorage
    private lateinit var clearButton: Button
    private lateinit var saveButton : Button
    private lateinit var binding: ActivitySightingsBinding
    private lateinit var database: DatabaseReference
    private lateinit var imageUri: Uri
    private lateinit var birdName: EditText
    private lateinit var birdLocation : EditText
    private lateinit var birdDescription : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySightingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val placeholderDrawable = resources.getDrawable(R.drawable.new_camera_icon)
        database = FirebaseDatabase.getInstance().getReference("Birds")
        storage = FirebaseStorage.getInstance()
        clearButton = findViewById(R.id.clearButton)
        saveButton = findViewById(R.id.saveButton)
        birdDescription = findViewById(R.id.uploadBirdDescription)
        birdLocation = findViewById(R.id.uploadBirdLocation)
        birdName = findViewById(R.id.uploadBirdName)

        binding.uploadImage.setOnClickListener {
            selectImage()
        }


        binding.saveButton.setOnClickListener {
            val birdName = binding.uploadBirdName.text.toString()
            val birdLocation = binding.uploadBirdLocation.text.toString()
            val birdDescription = binding.uploadBirdDescription.text.toString()

            val birds = BirdData(imageUri.toString(), birdName, birdLocation, birdDescription)

            database.child(birdName).setValue(birds)
                .addOnSuccessListener {
                    binding.uploadBirdName.text.clear()
                    binding.uploadBirdLocation.text.clear()
                    binding.uploadBirdDescription.text.clear()

                    Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()

                    // Start the birdsList activity
                    val intent = Intent(this, Gallery::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to save data: ${exception.message}", Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "Error writing data to Firebase: ${exception.message}", exception)
                }
            uploadImage()
        }

        bottomNavigationView = findViewById(R.id.bottomNavigation) //(Jansen Van Rensburg, 2024)
        bottomNavigationView.setSelectedItemId(R.id.navigation_add)
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
                    true
                }
                else -> false // Handle other cases here
            }


        }

        clearButton.setOnClickListener {
            birdName.text.clear()
            birdLocation.text.clear()
            birdDescription.text.clear()

            val imageView: ImageView = findViewById(R.id.uploadImage)
            imageView.setImageDrawable(placeholderDrawable)
        }


    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.uploadImage.setImageURI(imageUri)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                binding.uploadImage.setImageURI(null)
                Toast.makeText(this@AddSightings, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
            .addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()

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
