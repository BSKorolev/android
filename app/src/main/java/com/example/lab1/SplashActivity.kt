package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefs = getSharedPreferences("user_data", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthState()
        }, 1000)
        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    private fun checkAuthState() {
        val isAutoLogin = prefs.getBoolean("auto_login", false)
        val hasCredentials = prefs.contains("email") || prefs.contains("phone")

        when {
            isAutoLogin && hasCredentials -> navigateTo(ContentActivity::class.java)
            hasCredentials -> navigateTo(LoginActivity::class.java)
            else -> navigateTo(RegistrationActivity::class.java)
        }
        finish()
    }

    private fun <T> navigateTo(cls: Class<T>) {
        startActivity(Intent(this, cls))
    }

}