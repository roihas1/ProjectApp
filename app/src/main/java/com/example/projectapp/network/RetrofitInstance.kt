package com.example.projectapp.network

import android.util.Log
import com.example.projectapp.MyAppInstance
import com.example.projectapp.SessionManager
import okhttp3.HttpUrl
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class SessionInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val sessionId = sessionManager.getSessionId()
        val csrfToken = sessionManager.getCsrfToken()

        val newRequestBuilder = originalRequest.newBuilder()
        if (shouldNotAddHeaders(originalRequest.method,originalRequest.url.encodedPath)) {
            return chain.proceed(newRequestBuilder.build())
        }
        else if (shouldAddCsrfToken(originalRequest.method,originalRequest.url.encodedPath)) {
            csrfToken?.let {
                newRequestBuilder.addHeader("X-CSRFToken", it)
                newRequestBuilder.header("Cookie", "csrftoken=$it")
            }
            val newRequest = newRequestBuilder.build()
            return chain.proceed(newRequest)
        }
        csrfToken?.let {
            newRequestBuilder.addHeader("X-CSRFToken", it)
            newRequestBuilder.header("Cookie", "csrftoken=$it")
        }

        sessionId?.let {
            newRequestBuilder.addHeader("Cookie", "sessionid=$it")
        }
        val newRequest = newRequestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun shouldAddCsrfToken(method: String, url: String): Boolean {
        return url.endsWith("users/login/custom_login_system/")
                && method.equals("POST",ignoreCase = true)
    }

    private fun shouldNotAddHeaders(method: String,url: String): Boolean {
        return url.endsWith("users/login/custom_login_system/")
                && method.equals("GET",ignoreCase = true) || url.endsWith("users/signup/")
    }
}
object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.24:8000/"

    private val client by lazy {
        val sessionManager = SessionManager(MyAppInstance.context) // Pass your application context
        OkHttpClient.Builder()
            .addInterceptor(SessionInterceptor(sessionManager))
            .build()
    }

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
