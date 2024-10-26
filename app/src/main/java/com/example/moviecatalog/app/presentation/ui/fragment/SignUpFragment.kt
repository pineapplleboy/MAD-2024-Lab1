package com.example.moviecatalog.app.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.domain.model.Gender
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.usecase.SignUpUseCase
import com.example.moviecatalog.app.presentation.ui.activity.ProfileActivity
import com.example.moviecatalog.app.presentation.viewmodel.MoviesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.SignUpViewModel
import com.example.moviecatalog.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val vm by viewModel<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginChoiceFragment = LoginChoiceFragment()

        binding.returnFromSignUpButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, loginChoiceFragment)
                .addToBackStack(null)
                .commit()
        }

        var gender: Gender = Gender.MALE

        binding.maleButton.setOnClickListener {
            binding.maleButton.setBackgroundResource(R.drawable.male_button_orange)
            binding.femaleButton.setBackgroundResource(R.drawable.female_button_dark)
            gender = Gender.MALE
        }

        binding.femaleButton.setOnClickListener {
            binding.maleButton.setBackgroundResource(R.drawable.male_button_dark)
            binding.femaleButton.setBackgroundResource(R.drawable.female_button_orange)
            gender = Gender.FEMALE
        }

        binding.confirmSignUpButton.setOnClickListener {

            vm.signUp(
                userRegister = UserRegister(
                    login = binding.signUpLoginField.text.toString(),
                    email = binding.emailField.text.toString(),
                    name = binding.nameField.text.toString(),
                    password = binding.signUpPasswordField.text.toString(),
                    birthday = binding.birthdayField.text.toString(),
                    gender = gender.code
                )
            )
        }

        vm.signUpResult.observe(this) {
            if(it){
                val intent = Intent(view.context, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
}