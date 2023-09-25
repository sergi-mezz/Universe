package com.smdev.universe.planetbrowser.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.smdev.universe.R
import com.smdev.universe.data.Planet
import com.smdev.universe.databinding.FragmentPlanetDetailBinding
import com.smdev.universe.planetbrowser.detail.PlanetDetailFragmentArgs


class PlanetDetailFragment : Fragment() {

    lateinit var view: FragmentPlanetDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentPlanetDetailBinding.inflate(layoutInflater, container, false)
        return view.root
    }

    private val args: PlanetDetailFragmentArgs by navArgs()
    private val viewModel: PlanetDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.data)
        configureObservers()
        viewModel.fetchPlanetData(args.url)
    }

    private fun configureObservers() {
        viewModel.command.observe(viewLifecycleOwner, ::processCommand)
    }

    private fun processCommand(command: PlanetDetailViewModel.Companion.Command) {
        when(command) {
            is PlanetDetailViewModel.Companion.Command.ShowLoading -> { showLoading() }
            is PlanetDetailViewModel.Companion.Command.ShowSuccessfulResponse -> { viewModel.planetData?.let { showData(it) } }
            is PlanetDetailViewModel.Companion.Command.ShowError -> { showError(command.message) }
        }
    }

    private fun showLoading() {
        view.contentLayout.visibility = View.GONE
        view.progressBar.visibility = View.VISIBLE
    }


    private fun showData(planet: Planet) {
        with(view) {
            nameTextView.text = planet.name
            orbitTextView.text = "Orbit ${planet.orbital_period}"
            terrainTextView.text = planet.terrain
            climateTextView.text = planet.climate
            populationTextView.text = "Población ${planet.population}"
            progressBar.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        Snackbar.make(view.root, message, Snackbar.LENGTH_SHORT).show()
        view.progressBar.visibility = View.GONE
    }
}