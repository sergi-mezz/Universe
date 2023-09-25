package com.smdev.universe.planetbrowser.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smdev.universe.api.PlanetsApiClient
import com.smdev.universe.data.Planet
import kotlinx.coroutines.launch

class PlanetDetailViewModel: ViewModel() {

    val command = MutableLiveData<Command>()
    var planetData: Planet? = null

    fun fetchPlanetData(url: String) {
        if(planetData != null) {
            command.postValue(Command.ShowSuccessfulResponse)
        } else {
            command.value = Command.ShowLoading
            val service = PlanetsApiClient.planetsService
            viewModelScope.launch {
                try {
                    planetData = service.getPlanet(url)
                    command.postValue(Command.ShowSuccessfulResponse)
                } catch (e: Exception) {
                    command.postValue(Command.ShowError(e.message ?: "Error al consumir servicio"))
                }
            }
        }
    }

    companion object {
        sealed class Command() {
            object ShowSuccessfulResponse: Command()
            object ShowLoading: Command()
            class ShowError(val message: String): Command()
        }
    }
}