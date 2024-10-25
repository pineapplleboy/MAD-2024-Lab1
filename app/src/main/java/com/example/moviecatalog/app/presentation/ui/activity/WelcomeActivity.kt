package com.example.moviecatalog.app.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviecatalog.R
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.app.presentation.ui.fragment.LoginChoiceFragment

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginChoiceFragment = LoginChoiceFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.loginScreen, loginChoiceFragment)
            .addToBackStack(null)
            .commit()

        val authPreferences = AuthPreferences(this)
        if(authPreferences.getToken() != ""){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}