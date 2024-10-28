package com.example.moviecatalog.app.presentation.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.ui.activity.WelcomeActivity
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.databinding.FragmentProfileBinding
import com.example.moviecatalog.domain.usecase.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.LogOutUseCase
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personalInformationText: TextView = binding.personaInformationText

        val paint = personalInformationText.paint
        val width = paint.measureText(personalInformationText.text.toString())

        val shader = LinearGradient(0f, 0f, width, personalInformationText.textSize,
            intArrayOf(Color.parseColor("#DF2800"), Color.parseColor("#FF6633")),
            null, Shader.TileMode.CLAMP)

        personalInformationText.paint.shader = shader

        val authPreferences = AuthPreferences(view.context)
        val authApi = MovieCatalogApiInstance.createApi(authPreferences)

        val authRepository = AuthRepositoryImpl(authApi, authPreferences)
        val userProfileRepository = UserProfileRepositoryImpl(authApi)

        val logOutUseCase = LogOutUseCase(authRepository)
        val getUserProfileUseCase = GetUserProfileUseCase(userProfileRepository)

        binding.logOutButton.setOnClickListener{
            lifecycleScope.launch{
                val result = logOutUseCase.execute()
                result.onSuccess {
                    val intent = Intent(view.context, WelcomeActivity::class.java)
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
    }
}