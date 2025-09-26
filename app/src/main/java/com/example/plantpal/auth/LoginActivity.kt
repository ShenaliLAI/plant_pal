package com.example.plantpal.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantpal.databinding.ActivityLoginBinding
import android.content.Intent
import com.example.plantpal.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            val email = binding.inputEmail.text?.toString()?.trim().orEmpty()
            val password = binding.inputPassword.text?.toString()?.trim().orEmpty()

            when {
                email.isEmpty() -> binding.layoutEmail.error = getString(com.example.plantpal.R.string.error_required)
                else -> binding.layoutEmail.error = null
            }
            when {
                password.isEmpty() -> binding.layoutPassword.error = getString(com.example.plantpal.R.string.error_required)
                password.length < 6 -> binding.layoutPassword.error = getString(com.example.plantpal.R.string.error_password_length)
                else -> binding.layoutPassword.error = null
            }

            if (binding.layoutEmail.error == null && binding.layoutPassword.error == null) {
                // Navigate to Home Screen on successful login
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish() // Prevents user from returning to the login screen with the back button
            }
        }

        binding.txtForgotPassword.setOnClickListener {
            Toast.makeText(this, getString(com.example.plantpal.R.string.forgot_password_clicked), Toast.LENGTH_SHORT).show()
        }

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
