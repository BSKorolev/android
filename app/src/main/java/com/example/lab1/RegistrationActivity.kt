package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegistrationActivity : AppCompatActivity() {

    private var isEmailMode = true
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        prefs = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val btnEmail = findViewById<Button>(R.id.btnEmail)
        val btnPhone = findViewById<Button>(R.id.btnPhone)
        val etEmail = findViewById<EditText>(R.id.emailInput)
        val etPassword = findViewById<EditText>(R.id.passwordInput)
        val etConfirmPassword = findViewById<EditText>(R.id.confirmPasswordInput)
        val btnRegister = findViewById<Button>(R.id.registerButton)

        setupSwitchButtons(btnEmail, btnPhone, etEmail)
        setupRegistrationButton(btnRegister, etEmail, etPassword, etConfirmPassword)
    }

    private fun setupSwitchButtons(btnEmail: Button, btnPhone: Button, etEmail: EditText) {
        btnEmail.setOnClickListener {
            isEmailMode = true
            updateUI(btnEmail, btnPhone, etEmail)
        }

        btnPhone.setOnClickListener {
            isEmailMode = false
            updateUI(btnEmail, btnPhone, etEmail)
        }
    }

    private fun setupRegistrationButton(
        btnRegister: Button,
        etEmail: EditText,
        etPassword: EditText,
        etConfirmPassword: EditText
    ) {
        btnRegister.setOnClickListener {
            val input = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            when {
                input.isEmpty() -> showError("Заполните поле ${if (isEmailMode) "email" else "телефон"}", etEmail)
                password.isEmpty() -> showError("Введите пароль", etPassword)
                confirmPassword.isEmpty() -> showError("Подтвердите пароль", etConfirmPassword)
                password != confirmPassword -> showError("Пароли не совпадают", etConfirmPassword)
                password.length < 8 -> showError("Пароль должен быть не менее 8 символов", etPassword)
                isEmailMode && !isValidEmail(input) -> showError("Некорректный email", etEmail)
                !isEmailMode && !isValidPhone(input) -> showError("Некорректный телефон", etEmail)
                else -> {
                    saveUserData(input, password)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun isValidEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPhone(phone: String) =
        phone.startsWith("+") && phone.length in 10..15 && phone.substring(1).all { it.isDigit() }

    private fun updateUI(btnEmail: Button, btnPhone: Button, etEmail: EditText) {
        val selectedColor = ContextCompat.getColor(this, R.color.teal_700)
        val defaultColor = ContextCompat.getColor(this, R.color.button_default)

        btnEmail.setBackgroundColor(if (isEmailMode) selectedColor else defaultColor)
        btnPhone.setBackgroundColor(if (!isEmailMode) selectedColor else defaultColor)

        etEmail.apply {
            hint = if (isEmailMode) "Введите email" else "Введите телефон"
            inputType = if (isEmailMode) InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            else InputType.TYPE_CLASS_PHONE
            text?.clear()
        }
    }

    private fun saveUserData(input: String, password: String) {
        prefs.edit().apply {
            if (isEmailMode) putString("email", input) else putString("phone", input)
            putString("password", password)
            apply()
        }
    }

    private fun showError(message: String, field: EditText? = null) {
        field?.error = message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}