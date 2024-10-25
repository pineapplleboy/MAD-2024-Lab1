package com.example.moviecatalog.app.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.databinding.ActivityProfileBinding
import com.example.moviecatalog.domain.usecase.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.LogOutUseCase
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        val authApi = MovieCatalogApiInstance.createApi(authPreferences)

        val authRepository = AuthRepositoryImpl(authApi, authPreferences)
        val userProfileRepository = UserProfileRepositoryImpl(authApi)

        val logOutUseCase = LogOutUseCase(authRepository)
        val getUserProfileUseCase = GetUserProfileUseCase(userProfileRepository)

        binding.logOutButton.setOnClickListener{
            lifecycleScope.launch{
                val result = logOutUseCase.execute()
                result.onSuccess {
                    val intent = Intent(this@ProfileActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        lifecycleScope.launch{

            val result = getUserProfileUseCase.execute()

            result.onSuccess {
                binding.loginText.text = it.login
                binding.nameText.text = it.name
                binding.welcomeName.text = it.name
                binding.emailText.text = it.email
                binding.birthdateText.text = it.birthday
                binding.maleText.setBackgroundResource(if(it.gender == 0) R.drawable.male_button_orange else R.drawable.male_button_dark)
                binding.femaleText.setBackgroundResource(if(it.gender == 1) R.drawable.female_button_orange else R.drawable.female_button_dark)
            }
        }

        binding.moviesNavigation.setOnClickListener{
            val intent = Intent(this, MoviesActivity::class.java)
            startActivity(intent)
        }
    }
}