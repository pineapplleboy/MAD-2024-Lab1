package com.example.moviecatalog.app.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FragmentLoginChoiceBinding

class LoginChoiceFragment : Fragment() {

    private lateinit var binding: FragmentLoginChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            val signInFragment = SignInFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, signInFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.signUpButton.setOnClickListener {
            val signUpFragment = SignUpFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.loginScreen, signUpFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
