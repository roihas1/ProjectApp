package com.example.projectapp.model


data class SignUpRequest(
    val email: String,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val password1: String,
    val password2: String
)

