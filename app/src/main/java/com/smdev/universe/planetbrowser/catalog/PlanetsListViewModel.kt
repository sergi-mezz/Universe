package com.smdev.universe.planetbrowser.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smdev.universe.api.PlanetsApiClient
import com.smdev.universe.data.Planet
import kotlinx.coroutines.launch

class PlanetsListViewModel : ViewModel() {

    val command = MutableLiveData<Command>()
    val planetsList = MutableLiveData(listOf<Planet>())
    val error = MutableLiveData("")

    fun getPlanetsList() {

        if(planetsList.value?.isNotEmpty() == true) {
            command.value = Command.ShowSuccessfulResponse
        } else {
            command.value = Command.ShowLoading
            val service = PlanetsApiClient.planetsService
            viewModelScope.launch {
                try {
                    planetsList.postValue(service.getPlanets().results)
                    command.postValue(Command.ShowSuccessfulResponse)
                } catch (e: Exception) {
                    error.postValue(e.message)
                    command.postValue(Command.ShowError(e.message ?: "Error al consultar servicio"))
                }
            }
        }
    }

    companion object {
        sealed class Command {
            object ShowSuccessfulResponse: Command()
            class ShowError(val message: String): Command()
            object ShowLoading: Command()
        }
    }

}