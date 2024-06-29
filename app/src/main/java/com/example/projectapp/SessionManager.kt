package com.example.projectapp

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    companion object {
        private const val KEY_SESSION_ID = "SESSION_ID"
        private const val KEY_CSRF_TOKEN = "CSRF_TOKEN"
    }
    fun saveSessionId(sessionId: String) {
        sharedPreferences.edit().putString(KEY_SESSION_ID, sessionId).apply()
    }

    // Function to retrieve session ID
    fun getSessionId(): String? {
        return sharedPreferences.getString(KEY_SESSION_ID, null)
    }

    // Function to clear session ID (logout)
    fun clearSession() {
        sharedPreferences.edit().remove(KEY_SESSION_ID).apply()
        sharedPreferences.edit().remove(KEY_CSRF_TOKEN).apply()

    }
    // Function to save CSRF token
    fun saveCsrfToken(csrfToken: String) {
        sharedPreferences.edit().putString(KEY_CSRF_TOKEN, csrfToken).apply()
    }

    // Function to retrieve CSRF token
    fun getCsrfToken(): String? {
        return sharedPreferences.getString(KEY_CSRF_TOKEN, null)
    }
}