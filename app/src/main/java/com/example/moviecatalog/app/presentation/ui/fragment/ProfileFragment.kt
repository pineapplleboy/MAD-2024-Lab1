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
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.ui.activity.FriendsActivity
import com.example.moviecatalog.app.presentation.ui.activity.WelcomeActivity
import com.example.moviecatalog.app.presentation.viewmodel.ProfileViewModel
import com.example.moviecatalog.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var appComponent: AppComponent
    private lateinit var vm: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        appComponent = AppComponent(binding.root.context)
        vm = appComponent.provideProfileViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paint = binding.personaInformationText.paint
        val width = paint.measureText(binding.personaInformationText.text.toString())

        val shader = LinearGradient(
            0f, 0f, width, binding.personaInformationText.textSize,
            intArrayOf(Color.parseColor("#DF2800"), Color.parseColor("#FF6633")),
            null, Shader.TileMode.CLAMP
        )

        paint.shader = shader

        vm.profile.observe(viewLifecycleOwner) {
            if (it == null) {
                val intent = Intent(view.context, WelcomeActivity::class.java)
                startActivity(intent)
            } else {
                binding.loginText.text = it.login
                binding.nameText.text = it.name
                binding.welcomeName.text = it.name
                binding.emailText.text = it.email
                binding.birthdateText.text = changeDateFormat(it.birthday)
                binding.maleText.setBackgroundResource(if (it.gender == 0) R.drawable.male_button_orange else R.drawable.male_button_dark)
                binding.femaleText.setBackgroundResource(if (it.gender == 1) R.drawable.female_button_orange else R.drawable.female_button_dark)
            }
        }

        binding.logOutButton.setOnClickListener {
            vm.logOut()
        }

        binding.friendsPanel.setOnClickListener {
            val intent = Intent(view.context, FriendsActivity::class.java)
            startActivity(intent)
        }

        vm.getProfile()

        binding.welcomeWords.text = getGreeting()
    }

    private fun changeDateFormat(dateString: String): String {

        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date: Date? = serverDateFormat.parse(dateString)

        val displayDateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

        return date?.let { displayDateFormat.format(it) } ?: ""
    }

    private fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 6..11 -> getString(R.string.good_morning)
            in 12..17 -> getString(R.string.good_afternoon)
            in 18..23 -> getString(R.string.good_evening)
            else -> getString(R.string.good_night)
        }
    }
}