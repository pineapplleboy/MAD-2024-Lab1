package com.example.moviecatalog.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.AuthApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.domain.repository.UserProfileRepository
import com.example.moviecatalog.domain.usecase.LogOutUseCase

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val personalInformationText: TextView = findViewById(R.id.personaInformationText)

        val paint = personalInformationText.paint
        val width = paint.measureText(personalInformationText.text.toString())

        val shader = LinearGradient(0f, 0f, width, personalInformationText.textSize,
            intArrayOf(Color.parseColor("#DF2800"), Color.parseColor("#FF6633")),
            null, Shader.TileMode.CLAMP)

        personalInformationText.paint.shader = shader

        val authPreferences = AuthPreferences(this)
        val authApi = AuthApiInstance.createApi(authPreferences)

        val userProfileRepository = UserProfileRepositoryImpl(authApi, authPreferences)
        val logOutUseCase = LogOutUseCase(userProfileRepository)

        val logOutButton: ImageButton = findViewById(R.id.logOutButton)

        logOutButton.setOnClickListener{
            logOutUseCase.execute {
                if(it){
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}