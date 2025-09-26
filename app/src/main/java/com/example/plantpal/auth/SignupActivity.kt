package com.example.plantpal.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantpal.databinding.ActivitySignupBinding
import android.content.Intent

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            val name = binding.inputName.text?.toString()?.trim().orEmpty()
            val email = binding.inputEmail.text?.toString()?.trim().orEmpty()
            val password = binding.inputPassword.text?.toString()?.trim().orEmpty()

            when {
                name.isEmpty() -> binding.layoutName.error = getString(com.example.plantpal.R.string.error_required)
                else -> binding.layoutName.error = null
            }
            when {
                email.isEmpty() -> binding.layoutEmail.error = getString(com.example.plantpal.R.string.error_required)
                else -> binding.layoutEmail.error = null
            }
            when {
                password.isEmpty() -> binding.layoutPassword.error = getString(com.example.plantpal.R.string.error_required)
                password.length < 6 -> binding.layoutPassword.error = getString(com.example.plantpal.R.string.error_password_length)
                else -> binding.layoutPassword.error = null
            }

            if (binding.layoutName.error == null && binding.layoutEmail.error == null && binding.layoutPassword.error == null) {
                Toast.makeText(this, getString(com.example.plantpal.R.string.signup_success_placeholder), Toast.LENGTH_SHORT).show()
                // TODO: Hook up to real auth later
            }
        }

        binding.txtSignIn.setOnClickListener {
            // Navigate directly to LoginActivity without any popup messages
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish() // Close signup activity to prevent back button issues
        }
    }
}
