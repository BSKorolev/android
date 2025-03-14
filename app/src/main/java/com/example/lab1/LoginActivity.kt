package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var cbAutoLogin: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        cbAutoLogin = findViewById(R.id.cbAutoLogin)

        val etEmail = findViewById<EditText>(R.id.emailInput)
        val etPassword = findViewById<EditText>(R.id.passwordInput)
        val btnLogin = findViewById<Button>(R.id.loginButton)

        // Автозаполнение
        prefs.getString("email", null)?.let { etEmail.setText(it) }
        prefs.getString("phone", null)?.let { etEmail.setText(it) }

        btnLogin.setOnClickListener {
            val input = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            when {
                input.isEmpty() || password.isEmpty() -> showError("Заполните все поля")
                !isValidCredentials(input, password) -> showError("Неверные учетные данные")
                else -> {
                    saveAutoLoginState()
                    navigateToContent()
                }
            }
        }
    }

    private fun isValidCredentials(input: String, password: String): Boolean {
        val savedEmail = prefs.getString("email", null)
        val savedPhone = prefs.getString("phone", null)
        val savedPassword = prefs.getString("password", null)

        return (input == savedEmail || input == savedPhone) && password == savedPassword
    }

    private fun saveAutoLoginState() {
        prefs.edit().putBoolean("auto_login", cbAutoLogin.isChecked).apply()
    }

    private fun navigateToContent() {
        startActivity(Intent(this, ContentActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}