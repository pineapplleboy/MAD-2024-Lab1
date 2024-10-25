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
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val returnButton: ImageButton = view.findViewById(R.id.return_from_sign_up_button)
        val loginChoiceFragment = LoginChoiceFragment()

        returnButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, loginChoiceFragment)
                .addToBackStack(null)
                .commit()
        }

        val confirmButton: Button = view.findViewById(R.id.confirmSignUpButton)

        val authPreferences = AuthPreferences(view.context)
        val authApi = MovieCatalogApiInstance.createApi(authPreferences)

        val userProfileRepository = AuthRepositoryImpl(authApi, authPreferences)
        val signUpUseCase = SignUpUseCase(userProfileRepository)

        val loginField: EditText = view.findViewById(R.id.signUpLoginField)
        val emailField: EditText = view.findViewById(R.id.emailField)
        val nameField: EditText = view.findViewById(R.id.nameField)
        val passwordField: EditText = view.findViewById(R.id.signUpPasswordField)
        val confirmPasswordField: EditText = view.findViewById(R.id.signUpConfirmPasswordField)
        val birthdayField: EditText = view.findViewById(R.id.birthdayField)

        val maleField: Button = view.findViewById(R.id.maleButton)
        val femaleField: Button = view.findViewById(R.id.femaleButton)
        var gender: Gender = Gender.MALE

        maleField.setOnClickListener{
            maleField.setBackgroundResource(R.drawable.male_button_orange)
            femaleField.setBackgroundResource(R.drawable.female_button_dark)
            gender = Gender.MALE
        }

        femaleField.setOnClickListener{
            maleField.setBackgroundResource(R.drawable.male_button_dark)
            femaleField.setBackgroundResource(R.drawable.female_button_orange)
            gender = Gender.FEMALE
        }

        confirmButton.setOnClickListener{

            lifecycleScope.launch {
                val result = signUpUseCase.execute(
                    user = UserRegister(
                        login = loginField.text.toString(),
                        email = emailField.text.toString(),
                        name = nameField.text.toString(),
                        password = passwordField.text.toString(),
                        birthday = birthdayField.text.toString(),
                        gender = gender.code
                    )
                )

                result.onSuccess {
                    val intent = Intent(view.context, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}