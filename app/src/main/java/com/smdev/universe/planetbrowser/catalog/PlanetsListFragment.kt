package com.smdev.universe.planetbrowser.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.smdev.universe.R
import com.smdev.universe.databinding.FragmentPlanetsListBinding
import com.smdev.universe.planetbrowser.PlanetBrowserViewModel

class PlanetsListFragment : Fragment() {

    private val viewModel: PlanetsListViewModel by viewModels()
    private val activityViewModel: PlanetBrowserViewModel by activityViewModels()
    private lateinit var view: FragmentPlanetsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentPlanetsListBinding.inflate(layoutInflater, container, false)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
        configureObservers()
        viewModel.getPlanetsList()
    }

    private fun configureView() {
        with(view) {
            planetsList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            planetsList.adapter = PlanetsListAdapter(viewModel.planetsList.value!!, ::onCardClicked)
        }
    }

    private fun onCardClicked(url: String) {
        findNavController().navigate(R.id.Catalog_to_Detail, Bundle().apply {
            putString("url", url)
        })
    }

    private fun configureObservers() {
        viewModel.command.observe(viewLifecycleOwner, ::processCommand)
    }

    private fun processCommand(command: PlanetsListViewModel.Companion.Command) {
        when(command) {
            is PlanetsListViewModel.Companion.Command.ShowLoading -> { showLoading() }
            is PlanetsListViewModel.Companion.Command.ShowSuccessfulResponse -> { showPlanets() }
            is PlanetsListViewModel.Companion.Command.ShowError -> { showError(command.message) }
        }
    }

    private fun showLoading() {
        activityViewModel.command.value = PlanetBrowserViewModel.Companion.Command.ShowLoading
        view.progressBar.visibility = View.VISIBLE
        view.planetsList.visibility = View.GONE
    }

    private fun showPlanets() {
        activityViewModel.command.value = PlanetBrowserViewModel.Companion.Command.ShowDataLoaded
        view.progressBar.visibility = View.GONE
        view.planetsList.visibility = View.VISIBLE
        view.planetsList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.planetsList.adapter = PlanetsListAdapter(
            viewModel.planetsList.value!!,
            ::onCardClicked
        )
    }

    private fun showError(error: String) {
        activityViewModel.command.value = PlanetBrowserViewModel.Companion.Command.ShowError(error)
        view.progressBar.visibility = View.GONE
        Snackbar.make(view.root, error, Snackbar.LENGTH_SHORT).show()
    }

}