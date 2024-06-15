package com.example.projectapp.network

import retrofit2.Response
import com.example.projectapp.model.LoginRequest
import com.example.projectapp.model.LoginResponse
import com.example.projectapp.model.SignUpRequest
import com.example.projectapp.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users/login/check/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("users/signup/")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>
}
