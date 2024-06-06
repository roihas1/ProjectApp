package com.example.projectapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.projectapp.model.LoginRequest
import com.example.projectapp.model.LoginResponse
import com.example.projectapp.model.SignUpRequest
import com.example.projectapp.model.SignUpResponse
import com.example.projectapp.network.RetrofitInstance

import kotlinx.coroutines.launch
import retrofit2.Response



class AuthViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")
    var rePassword by mutableStateOf("")
    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
    var signUpState by mutableStateOf<SignUpState>(SignUpState.Idle)

    fun login(navController: NavController) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            try {
                val response = RetrofitInstance.api.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    loginState = LoginState.Success(response.body())
                    navController.navigate("HomeScreen")
                } else {
                    loginState = LoginState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                loginState = LoginState.Error("An error occurred: ${e.message}")
            }
        }
    }

    fun signUp(navController: NavController) {
        if (password != rePassword) {
            signUpState = SignUpState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            signUpState = SignUpState.Loading
            try {
                val response = RetrofitInstance.api.signUp(SignUpRequest(username, email, password))
                if (response.isSuccessful) {
                    signUpState = SignUpState.Success(response.body())
                    navController.navigate("HomeScreen")
                } else {
                    signUpState = SignUpState.Error("Sign-up failed: ${response.message()}")
                }
            } catch (e: Exception) {
                signUpState = SignUpState.Error("An error occurred: ${e.message}")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginResponse?) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class SignUpState {


    object Idle : SignUpState()
    object Loading : SignUpState()
    data class Success(val data: SignUpResponse?) : SignUpState()
    data class Error(val message: String) : SignUpState()
}
