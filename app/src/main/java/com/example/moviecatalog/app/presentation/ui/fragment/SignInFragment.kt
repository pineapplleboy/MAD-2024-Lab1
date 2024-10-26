package com.example.moviecatalog.app.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.app.presentation.ui.activity.ProfileActivity
import com.example.moviecatalog.app.presentation.viewmodel.SignInViewModel
import com.example.moviecatalog.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val vm by viewModel<SignInViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginChoiceFragment = LoginChoiceFragment()

        binding.returnFromSignInButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, loginChoiceFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.confirmLoginButton.setOnClickListener{

            vm.signIn(
                LoginCredentials(
                    login = binding.loginField.text.toString(),
                    password = binding.passwordField.text.toString(),
                )
            )
        }

        vm.signInResult.observe(this){
            if(it){
                val intent = Intent(view.context, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
}