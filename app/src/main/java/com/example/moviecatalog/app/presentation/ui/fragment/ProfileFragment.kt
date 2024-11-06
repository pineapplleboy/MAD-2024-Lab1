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
import com.example.moviecatalog.app.presentation.ui.activity.FriendsActivity
import com.example.moviecatalog.app.presentation.ui.activity.WelcomeActivity
import com.example.moviecatalog.app.presentation.viewmodel.ProfileViewModel
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.databinding.FragmentProfileBinding
import com.example.moviecatalog.domain.usecase.profile.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.profile.LogOutUseCase
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val vm by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paint = binding.personaInformationText.paint
        val width = paint.measureText(binding.personaInformationText.text.toString())

        val shader = LinearGradient(0f, 0f, width, binding.personaInformationText.textSize,
            intArrayOf(Color.parseColor("#DF2800"), Color.parseColor("#FF6633")),
            null, Shader.TileMode.CLAMP)

        paint.shader = shader

        vm.profile.observe(this){
            if(it == null){
                val intent = Intent(view.context, WelcomeActivity::class.java)
                startActivity(intent)
            }
            else{
                binding.loginText.text = it.login
                binding.nameText.text = it.name
                binding.welcomeName.text = it.name
                binding.emailText.text = it.email
                binding.birthdateText.text = changeDateFormat(it.birthday)
                binding.maleText.setBackgroundResource(if(it.gender == 0) R.drawable.male_button_orange else R.drawable.male_button_dark)
                binding.femaleText.setBackgroundResource(if(it.gender == 1) R.drawable.female_button_orange else R.drawable.female_button_dark)
            }
        }

        binding.logOutButton.setOnClickListener{
            vm.logOut()
        }

        binding.friendsPanel.setOnClickListener{
            val intent = Intent(view.context, FriendsActivity::class.java)
            startActivity(intent)
        }

        vm.getProfile()
    }

    private fun changeDateFormat(dateString: String): String {

        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date: Date? = serverDateFormat.parse(dateString)

        val displayDateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

        return date?.let { displayDateFormat.format(it) } ?: ""
    }
}