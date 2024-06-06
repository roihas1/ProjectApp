package com.example.projectapp.model

data class LoginResponse(
    val token: String,  // Example field, adjust based on your actual response structure
    val user: User,
    val isSuccessful:Boolean
)

data class User(
    val id: String,
    val name: String,
    val email: String
)
