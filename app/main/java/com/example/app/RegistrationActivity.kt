package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import android.widget.TextView

class RegistrationActivity : AppCompatActivity() {
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var registerButton: MaterialButton
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)
        loginLink = findViewById(R.id.loginLink)
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            if (validateInputs()) {
                // TODO: Implement actual registration logic here
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        loginLink.setOnClickListener {
            // TODO: Navigate to login screen
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        if (name.isEmpty()) {
            nameInput.error = "Введите имя"
            return false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Введите корректный email"
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            passwordInput.error = "Пароль должен содержать минимум 6 символов"
            return false
        }

        if (password != confirmPassword) {
            confirmPasswordInput.error = "Пароли не совпадают"
            return false
        }

        return true
    }
} 