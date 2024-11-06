package com.example.moviecatalog.app.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.viewmodel.LaunchViewModel

class LaunchActivity : AppCompatActivity() {

    private lateinit var appComponent: AppComponent
    private lateinit var vm: LaunchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_launch)

        appComponent = AppComponent(this)
        vm = appComponent.provideLaunchViewModel()

        vm.getProfile()

        vm.result.observe(this) {
            if (it == 1) {
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            } else if (it == 2) {
                val intent = Intent(this, WelcomeActivity::class.java)

                startActivity(intent)
            }
        }
    }
}