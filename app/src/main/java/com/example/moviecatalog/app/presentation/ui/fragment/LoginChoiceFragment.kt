package com.example.moviecatalog.app.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.moviecatalog.R

class LoginChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInButton: Button = view.findViewById(R.id.signInButton)
        val signUpButton: Button = view.findViewById(R.id.signUpButton)

        signInButton.setOnClickListener{
            val signInFragment = SignInFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, signInFragment)
                .addToBackStack(null)
                .commit()
        }

        signUpButton.setOnClickListener{
            val signUpFragment = SignUpFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, signUpFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
