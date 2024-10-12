package com.example.moviecatalog.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.AuthApiInstance
import com.example.moviecatalog.data.model.ApiGender
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.usecase.SignUpUseCase
import com.example.moviecatalog.presentation.ui.activity.ProfileActivity

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
        val authApi = AuthApiInstance.createApi(authPreferences)

        val userProfileRepository = AuthRepositoryImpl(authApi, authPreferences)
        val signUpUseCase = SignUpUseCase(userProfileRepository)

        val loginField: EditText = view.findViewById(R.id.signUpLoginField)
        val emailField: EditText = view.findViewById(R.id.emailField)
        val nameField: EditText = view.findViewById(R.id.nameField)
        val passwordField: EditText = view.findViewById(R.id.signUpPasswordField)
        val confirmPasswordField: EditText = view.findViewById(R.id.signUpConfirmPasswordField)
        val birthdayField: EditText = view.findViewById(R.id.birthdayField)

        confirmButton.setOnClickListener{

            signUpUseCase.execute(
                user = UserRegister(
                    login = loginField.text.toString(),
                    email = emailField.text.toString(),
                    name = nameField.text.toString(),
                    password = passwordField.text.toString(),
                    birthday = birthdayField.text.toString(),
                    gender = ApiGender.MALE.code
                )
            ){
                if(it){
                    val intent = Intent(view.context, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}