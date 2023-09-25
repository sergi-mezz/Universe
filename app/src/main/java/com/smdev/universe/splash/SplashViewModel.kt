package com.smdev.universe.splash

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smdev.universe.data.UniverseAppDatabase
import com.smdev.universe.data.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    val timeout = MutableLiveData(false)
    val navigationCommand = MutableLiveData<Command>(Command.None)

    fun showSplash() {
        viewModelScope.launch {
            delay(5000)
            timeout.postValue(true)
        }
    }

    fun anyUsers(applicationContext: Context) {
        val dao = UniverseAppDatabase.getDatabase(applicationContext).userDao()
        viewModelScope.launch {
            val users = dao.getUsers()
            if(users.isEmpty()) {
                navigationCommand.postValue(Command.NavigateToLogin)
            } else {
                navigationCommand.postValue(Command.NavigateToCatalog)
            }
        }
    }

    companion object {
        sealed class Command {
            object None: Command()
            object NavigateToLogin: Command()
            object NavigateToCatalog: Command()
        }
    }
}