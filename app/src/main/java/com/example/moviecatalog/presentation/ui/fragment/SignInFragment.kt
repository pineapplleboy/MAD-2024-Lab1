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
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.usecase.SignInUseCase
import com.example.moviecatalog.domain.usecase.SignUpUseCase
import com.example.moviecatalog.presentation.ui.activity.ProfileActivity

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val returnButton: ImageButton = view.findViewById(R.id.return_from_sign_in_button)
        val loginChoiceFragment = LoginChoiceFragment()

        returnButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, loginChoiceFragment)
                .addToBackStack(null)
                .commit()
        }

        val confirmButton: Button = view.findViewById(R.id.confirm_login_button)

        val authPreferences = AuthPreferences(view.context)
        val authApi = AuthApiInstance.createApi(authPreferences)

        val userProfileRepository = UserProfileRepositoryImpl(authApi, authPreferences)
        val signInUseCase = SignInUseCase(userProfileRepository)

        val loginField: EditText = view.findViewById(R.id.loginField)
        val passwordField: EditText = view.findViewById(R.id.passwordField)

        confirmButton.setOnClickListener{

            signInUseCase.execute(
                LoginCredentials(
                    login = loginField.text.toString(),
                    password = passwordField.text.toString(),
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