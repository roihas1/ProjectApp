package com.example.projectapp.network

import com.example.projectapp.model.AddInvestmentResponse
import com.example.projectapp.model.FirstDataResponse
import com.example.projectapp.model.Form1Request
import com.example.projectapp.model.Form1Response
import retrofit2.Response
import com.example.projectapp.model.LoginRequest
import com.example.projectapp.model.LoginResponse
import com.example.projectapp.model.SignUpRequest
import com.example.projectapp.model.SignUpResponse
import com.example.projectapp.model.StockWeightsResponse
import com.example.projectapp.model.addInvestmentRequest
import com.example.projectapp.model.form2Request

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/users/login/custom_login_system/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @GET("users/login/custom_login_system/")
    suspend fun getToken():Response<LoginResponse>
    @POST("/users/signup/")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/our_core/form/1/")
    suspend fun form1(@Body request: Form1Request): Response<Form1Response>
    @POST("/our_core/form/2/")
    suspend fun form2(@Body request: form2Request): Response<Form1Response>

    @GET("/data_mgmt/get_plot_data/1/")
    suspend fun getPlotData1():Response<FirstDataResponse>

    @GET("/data_mgmt/get_plot_data/2/")
    suspend fun getPlotData2():Response<StockWeightsResponse>

    @POST("users/logout/")
    suspend fun logout(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/investment/add/")
    suspend fun addInvestment(@Body request: addInvestmentRequest):Response<AddInvestmentResponse>
}
