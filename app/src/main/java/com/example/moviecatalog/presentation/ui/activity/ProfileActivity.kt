package com.example.moviecatalog.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.AuthApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.domain.usecase.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.LogOutUseCase
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val authRepository = AuthRepositoryImpl(authApi, authPreferences)
        val userProfileRepository = UserProfileRepositoryImpl(authApi)

        val logOutUseCase = LogOutUseCase(authRepository)
        val getUserProfileUseCase = GetUserProfileUseCase(userProfileRepository)

        val logOutButton: ImageButton = findViewById(R.id.logOutButton)

        logOutButton.setOnClickListener{
            logOutUseCase.execute {
                if(it){
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        val loginField: TextView = findViewById(R.id.loginText)
        val nameField: TextView = findViewById(R.id.nameText)
        val emailField: TextView = findViewById(R.id.emailText)
        val birthdayField: TextView = findViewById(R.id.birthdateText)
        val genderMaleField: Button = findViewById(R.id.maleText)
        val genderFemaleField: Button = findViewById(R.id.femaleText)
        val profileAvatar: ImageView = findViewById(R.id.profileAvatar)
        val welcomeNameText: TextView = findViewById(R.id.welcomeName)

        getUserProfileUseCase.execute{
            loginField.text = it.login
            nameField.text = it.name
            welcomeNameText.text = it.name
            emailField.text = it.email
            birthdayField.text = it.birthday
            genderMaleField.setBackgroundResource(if(it.gender == 0) R.drawable.male_button_orange else R.drawable.male_button_dark)
            genderFemaleField.setBackgroundResource(if(it.gender == 1) R.drawable.female_button_orange else R.drawable.female_button_dark)
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigationPanel)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    true
                }
                R.id.navigation_movies -> {
                    val intent = Intent(this, MoviesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_feed -> {
                    true
                }
                R.id.navigation_library -> {
                    true
                }
                else -> false
            }
        }
    }
}