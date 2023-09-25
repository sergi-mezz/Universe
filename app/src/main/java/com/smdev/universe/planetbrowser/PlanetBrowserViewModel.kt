package com.smdev.universe.planetbrowser

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smdev.universe.data.UniverseAppDatabase
import com.smdev.universe.data.User
import com.smdev.universe.login.LoginViewModel
import kotlinx.coroutines.launch

class PlanetBrowserViewModel: ViewModel() {

    val command = MutableLiveData<Command>(Command.ShowLoading)

    fun logOut(context: Context) {
        command.value = Command.ShowLoading
        val dao = UniverseAppDatabase.getDatabase(context).userDao()
        viewModelScope.launch {
            try {
                val user = dao.getUsers().first()
                dao.deleteUser(user)
                command.postValue(Command.RestartApp)
            } catch (e: Exception) {
                command.postValue(Command.ShowError(e.message ?: "Error al cerrar sesión"))
            }
        }
    }

    companion object {
        sealed class Command {
            object RestartApp: Command()
            class ShowError(val message: String): Command()
            object ShowLoading:Command()
            object ShowDataLoaded: Command()
        }
    }

}