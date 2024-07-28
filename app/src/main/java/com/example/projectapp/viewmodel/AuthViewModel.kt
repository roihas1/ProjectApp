package com.example.projectapp.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.projectapp.SessionManager
import com.example.projectapp.model.LoginRequest
import com.example.projectapp.model.LoginResponse
import com.example.projectapp.model.SignUpRequest
import com.example.projectapp.model.SignUpResponse
import com.example.projectapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response



class AuthViewModel : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var password by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var rePassword by mutableStateOf("")
    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
    var signUpState by mutableStateOf<SignUpState>(SignUpState.Idle)
    var token by mutableStateOf("")

    private fun extractCsrfToken(cookies: List<String>): String? {
        return cookies.firstOrNull { it.startsWith("csrftoken=") }
            ?.substringAfter("csrftoken=")
            ?.substringBefore(";")
    }
    private fun extractSessionId(cookies: List<String>): String? {
        return cookies.firstOrNull { it.startsWith("sessionid=") }
            ?.substringAfter("sessionid=")
            ?.substringBefore(";")
    }
    fun login(navController: NavController,sessionManager: SessionManager) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            try {
//                val tokenResponse = RetrofitInstance.api.getToken()
////                var cookies = tokenResponse.headers().values("Set-Cookie")
////                Log.i("login-cookie",tokenResponse.headers().values("Set-Cookie").toString())
////                var csrfToken = extractCsrfToken(cookies)
////                if (csrfToken != null) {
////                    sessionManager.saveCsrfToken(csrfToken)
////                }
                val response = RetrofitInstance.api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val body = response.body()
                        firstName = body?.user?.first_name.toString()
                        lastName = body?.user?.last_name.toString()
                        token = body?.token.toString()

                        Log.i("login-cookie",response.headers().values("Set-Cookie").toString())
                        val cookies = response.headers().values("Set-Cookie")
                        val sessionId = extractSessionId(cookies)
                        val csrfToken = extractCsrfToken(cookies)
                        if (sessionId != null) {
                            Log.i("infoooo-session", sessionId)
                            sessionManager.saveSessionId(sessionId)
                        }
                        csrfToken?.let { sessionManager.saveCsrfToken(it) }
                        if (csrfToken != null) {
                            Log.i("infoooo", csrfToken)
                        }
                        loginState = LoginState.Success(response.body())

                        Log.i("info", "helloooo $token" )
                        navController.navigate("HomeScreen")
                    }
                 } else {
                    Log.i("login-cookie",response.toString())
                    loginState = LoginState.Error("Login failed: ${response.message()}")

                }
            } catch (e: Exception) {
                loginState = LoginState.Error("An error occurred: ${e.message}")
                Log.e("error","An error occurred: ${e.message}")

            }
        }
    }

    fun signUp(navController: NavController) {

        viewModelScope.launch {
            signUpState = SignUpState.Loading
            try {
                val response = RetrofitInstance.api.signUp(SignUpRequest(
                    email,firstName,lastName,phoneNumber,  password, rePassword
                ))
//                val response = RetrofitInstance.api.signUp(SignUpRequest(
//                   email,email,password
//                ))
                Log.i("heleee",response.toString())
                if (response.isSuccessful) {
                    signUpState = SignUpState.Success(response.body())
                    navController.navigate("LoginScreen")
                } else {
                    signUpState = SignUpState.Error("Sign-up failed: ${response.message()}")
                }
            } catch (e: Exception) {
                signUpState = SignUpState.Error("An error occurred: ${e.message}")
            }
        }
    }
    fun logout(navController: NavController,sessionManager: SessionManager){
        viewModelScope.launch {
            val response = RetrofitInstance.api.logout(LoginRequest(email,password))
            if (response.isSuccessful){
                resetState()
                sessionManager.clearSession()
                navController.navigate("welcomeScreen")
            }
        }
    }
    fun resetState() {
        firstName = ""
        lastName = ""
        phoneNumber = ""
        password = ""
        email = ""
        rePassword = ""
        loginState = LoginState.Idle
        signUpState = SignUpState.Idle
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
