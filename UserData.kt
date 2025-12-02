package com.example.birdtrail
import java.io.Serializable

data class UserData(
    val id: String? = null,
    val username:String? = null,
    val email:String? = null,
    val password: String? = null,
    val bio: String? = null
) : Serializable

