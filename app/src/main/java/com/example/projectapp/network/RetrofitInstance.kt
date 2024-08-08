package com.example.projectapp.network

import android.util.Log
import com.example.projectapp.MyAppInstance
import com.example.projectapp.SessionManager
import okhttp3.HttpUrl

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit


class SessionInterceptor(
    private val sessionManager: SessionManager,
    private val cookieJar: CookieJar
) : Interceptor {

    companion object {
        private const val LOGIN_PATH = "users/login/custom_login_system/"
        private const val SIGNUP_PATH = "users/signup/"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val sessionId = sessionManager.getSessionId()
        val csrfToken = sessionManager.getCsrfToken()
        val newRequestBuilder = originalRequest.newBuilder()

        if (shouldNotAddHeaders(originalRequest.method, originalRequest.url.encodedPath)) {
            return chain.proceed(newRequestBuilder.build())
        } else if (shouldAddCsrfToken(originalRequest.method, originalRequest.url.encodedPath)) {
            addCsrfTokenToRequest(newRequestBuilder, csrfToken, originalRequest.url)
            val newRequest = newRequestBuilder.build()
            return chain.proceed(newRequest)
        }

        // For all other cases
        addCsrfTokenToRequest(newRequestBuilder, csrfToken, originalRequest.url)
        addSessionIdToRequest(newRequestBuilder, sessionId, originalRequest.url)
        val newRequest = newRequestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun addCsrfTokenToRequest(builder: Request.Builder, csrfToken: String?, url: HttpUrl) {
        csrfToken?.let { token ->
            builder.addHeader("X-CSRFToken", token)

            val csrfCookie = Cookie.Builder()
                .name("csrftoken")
                .value(token)
                .domain(url.host)
                .path("/")
                .build()

            val existingCookies = cookieJar.loadForRequest(url).toMutableList()
            existingCookies.removeAll { it.name == "csrftoken" }
            existingCookies.add(csrfCookie)
            cookieJar.saveFromResponse(url, existingCookies)
        }
    }

    private fun addSessionIdToRequest(builder: Request.Builder, sessionId: String?, url: HttpUrl) {
        sessionId?.let { id ->
            val sessionCookie = Cookie.Builder()
                .name("sessionid")
                .value(id)
                .domain(url.host)
                .path("/")
                .build()

            val existingCookies = cookieJar.loadForRequest(url).toMutableList()
            existingCookies.removeAll { it.name == "sessionid" }
            existingCookies.add(sessionCookie)
            cookieJar.saveFromResponse(url, existingCookies)
        }
    }

    private fun shouldAddCsrfToken(method: String, url: String): Boolean {
        return url.endsWith(LOGIN_PATH) && method.equals("POST", ignoreCase = true)
    }

    private fun shouldNotAddHeaders(method: String, url: String): Boolean {
        return (url.endsWith(LOGIN_PATH) && method.equals("GET", ignoreCase = true)) ||
                url.endsWith(SIGNUP_PATH)
    }
}
class InMemoryCookieJar : CookieJar {
    private val cookieStore = mutableMapOf<String, List<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url.host] ?: emptyList()
    }
}
object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.19:8000/"
    private const val TIMEOUT = 30L // 30 seconds

    private val cookieJar = InMemoryCookieJar()

    private val client by lazy {
        val sessionManager = SessionManager(MyAppInstance.context)
        OkHttpClient.Builder()
            .addInterceptor(SessionInterceptor(sessionManager, cookieJar))
            .cookieJar(cookieJar)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
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