package com.example.moviecatalog.app.presentation.ui.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.domain.model.Gender
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.app.presentation.ui.activity.MainActivity
import com.example.moviecatalog.app.presentation.viewmodel.SignUpViewModel
import com.example.moviecatalog.databinding.FragmentSignUpBinding
import java.util.Locale

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private lateinit var appComponent: AppComponent
    private lateinit var vm: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        appComponent = AppComponent(view.context)
        vm = appComponent.provideSignUpViewModel()


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

        val calendar = Calendar.getInstance()

        binding.setBirthdayButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                view.context,
                { _, year, month, dayOfMonth ->

                    calendar.set(year, month, dayOfMonth)

                    val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
                    val formattedDate = dateFormat.format(calendar.time)
                    binding.birthdayField.setText(formattedDate)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        binding.confirmSignUpButton.setOnClickListener {

            if(binding.signUpPasswordField.text.toString() != binding.signUpConfirmPasswordField.text.toString()){
                Toast.makeText(view.context,
                    getString(R.string.not_equal_passwords), Toast.LENGTH_SHORT).show()
            }

            else if(binding.signUpLoginField.text.toString() == ""){
                Toast.makeText(view.context,
                    getString(R.string.no_login), Toast.LENGTH_SHORT).show()
            }

            else if(binding.nameField.text.toString() == ""){
                Toast.makeText(view.context,
                    getString(R.string.no_name), Toast.LENGTH_SHORT).show()
            }

            else if(binding.signUpPasswordField.text.toString().length < 6){
                Toast.makeText(view.context,
                    getString(R.string.small_password), Toast.LENGTH_SHORT).show()
            }

            else if(binding.birthdayField.toString() == ""){
                Toast.makeText(view.context,
                    getString(R.string.no_birthday), Toast.LENGTH_SHORT).show()
            }

            else{
                vm.signUp(
                    userRegister = UserRegister(
                        login = binding.signUpLoginField.text.toString(),
                        email = binding.emailField.text.toString(),
                        name = binding.nameField.text.toString(),
                        password = binding.signUpPasswordField.text.toString(),
                        birthday = SimpleDateFormat("yyyy-MM-dd", Locale("ru")).format(calendar.time),
                        gender = gender.code
                    )
                )
            }
        }

        vm.signUpResult.observe(viewLifecycleOwner) {
            if(it){
                val intent = Intent(view.context, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}