BirdTrail – Bird Tracking & Observation App

A Kotlin-based Android mobile application for bird watchers.

Overview

BirdTrail is a mobile application designed for bird tracking, sightings management, and exploration of bird sanctuaries. Built with Kotlin and Firebase, the app allows users to log in, register, track bird hotspots, upload sightings, and view achievements earned through their activity.

This project highlights modern Android development practices, Google Maps integration, real-time data synchronization, and clean UI navigation.

✨ Features
🔐 User Authentication

Secure login and registration using Firebase Authentication

Input validation and smooth navigation for new and returning users

🗺️ Explore Page (Google Maps)

Displays bird sanctuary hotspots

Real-time location access

Custom map markers

Allows users to navigate to nearby bird-watching locations

📸 Add Bird Sightings

Upload bird images from the device

Add bird name, description, and location

Images uploaded to Firebase Storage

Data stored in Firebase Realtime Database

🐦 Sightings Gallery

Full list of user bird sightings displayed in a RecyclerView

Each card shows:

Bird image

Bird name

Description

Location

🏆 Achievements Page

Tracks user progress

Unlocks badges (e.g., First Sighting, Explorer, Frequent Observer)

Motivates users through gamified rewards

🏠 Home Dashboard

Central navigation hub

Quick access to Explore, Add Sightings, Gallery, Achievements, and Profile

🛠️ Tech Stack
Languages & Frameworks

Kotlin

Android SDK

APIs & Services

Firebase Authentication – User login and registration

Firebase Realtime Database – Store bird sightings and user data

Firebase Storage – Upload and retrieve bird images

Google Maps API – Display bird sanctuary hotspots

Android Components

Activities

RecyclerView & Adapter

Intents

Permissions (Camera, Storage, Location)

📁 Project Structure
File / Folder	Description
LoginActivity.kt	Handles Firebase user login
RegisterActivity.kt	Manages account creation
HomeActivity.kt	Main dashboard with navigation
ExploreActivity.kt	Google Maps hotspot exploration
AddSightingsActivity.kt	Upload bird images & info
SightingsActivity.kt	Displays list of sightings
SightingsAdapter.kt	RecyclerView adapter
AchievementsActivity.kt	Tracks unlocked badges
UserData.kt	User model for Firebase
Sightings.kt	Bird sighting model
🚀 How It Works

User registers using email & password

Login redirects to the Home Dashboard

Explore page shows nearby birding hotspots

User adds a sighting → Image + Name + Description + Location

Sightings saved to Firebase (with image URLs)

Gallery displays all sightings in a clean UI

Achievements unlock automatically as the user interacts with the app
