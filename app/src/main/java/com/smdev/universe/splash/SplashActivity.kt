package com.smdev.universe.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smdev.universe.R
import com.smdev.universe.databinding.ActivitySplashBinding
import com.smdev.universe.login.LoginActivity
import com.smdev.universe.planetbrowser.PlanetBrowserActivity

class SplashActivity: AppCompatActivity() {

    private lateinit var view: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(view.root)
        this.view.lottie.playAnimation()
        viewModel.navigationCommand.observe(this, ::processCommand)
        viewModel.timeout.observe(this, ::processTimeout)
        viewModel.showSplash()
        viewModel.anyUsers(applicationContext)
    }


    private fun processTimeout(timeout: Boolean) {
        if(timeout && viewModel.navigationCommand.value !is SplashViewModel.Companion.Command.None) {
            processCommand(viewModel.navigationCommand.value!!)
        }
    }

    private fun processCommand(command: SplashViewModel.Companion.Command) {
        when(command) {
            is SplashViewModel.Companion.Command.NavigateToLogin -> { navigateToLogin() }
            is SplashViewModel.Companion.Command.NavigateToCatalog -> { navigateToCatalog() }
            is SplashViewModel.Companion.Command.None -> {}
        }
    }

    private fun navigateToLogin() {
        if(viewModel.timeout.value == true) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun navigateToCatalog() {
        if(viewModel.timeout.value == true) {
            startActivity(Intent(this, PlanetBrowserActivity::class.java))
            finish()
        }
    }
}