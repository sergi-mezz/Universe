package com.smdev.universe.planetbrowser

import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.smdev.universe.R
import com.smdev.universe.databinding.ActivityPlanetBrowserBinding
import com.smdev.universe.splash.SplashActivity

class PlanetBrowserActivity : AppCompatActivity() {

    lateinit var view: ActivityPlanetBrowserBinding
    private val viewModel: PlanetBrowserViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityPlanetBrowserBinding.inflate(layoutInflater)
        setContentView(view.root)
        configureObservers()
        configureToolBar()
        view.logOutFab.setOnClickListener {
            viewModel.logOut(applicationContext)
        }
    }

    private fun configureObservers() {
        viewModel.command.observe(this, ::processCommand)
    }

    private fun configureToolBar() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(view.myToolbar, navController)
        setSupportActionBar(view.myToolbar)
        view.myToolbar.setupWithNavController(navController)
    }

    private fun showSnackbar(message: String, length: Int) {
        Snackbar.make(view.coordinator, message, length).show()
    }

    private fun processCommand(command: PlanetBrowserViewModel.Companion.Command) {
        when(command) {
            is PlanetBrowserViewModel.Companion.Command.ShowLoading -> { showLoading() }
            is PlanetBrowserViewModel.Companion.Command.RestartApp -> { restartApp() }
            is PlanetBrowserViewModel.Companion.Command.ShowError -> { showError(command.message) }
            is PlanetBrowserViewModel.Companion.Command.ShowDataLoaded -> { }
        }
    }

    private fun showError(message: String) {
        showSnackbar(message, Snackbar.LENGTH_LONG)
    }

    private fun restartApp() {
        showSnackbar("Cerrando sesión...", Snackbar.LENGTH_LONG)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    private fun showLoading() {
        showSnackbar(getString(R.string.catalog_welocme_message), Snackbar.LENGTH_SHORT)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

}