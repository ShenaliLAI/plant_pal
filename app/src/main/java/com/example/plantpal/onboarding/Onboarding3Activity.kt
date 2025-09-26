package com.example.plantpal.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plantpal.databinding.ActivityOnboarding3Binding
import com.example.plantpal.auth.SignupActivity

class Onboarding3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboarding3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Previous -> Onboarding2
        binding.btnPrev.setOnClickListener {
            startActivity(Intent(this, Onboarding2Activity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        // Get Started -> Signup
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        // Skip -> MainActivity
        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, com.example.plantpal.MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
