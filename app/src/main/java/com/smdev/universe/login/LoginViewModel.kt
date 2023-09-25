package com.smdev.universe.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smdev.universe.data.UniverseAppDatabase
import com.smdev.universe.data.User
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class LoginViewModel: ViewModel() {

    val command = MutableLiveData<Command>()
    var birthDate: Calendar = Calendar.getInstance().apply { time = Date() }

    fun saveUser(user: User, applicationContext: Context) {
        val dao = UniverseAppDatabase.getDatabase(applicationContext).userDao()
        viewModelScope.launch {
            try {
                dao.saveUser(user)
                command.postValue(Command.NavigateToCatalog)
            } catch (e: Exception) {
                command.postValue(Command.ShowError(e.message ?: "Error al guardar usuario"))
            }
        }
    }

    companion object {
        sealed class Command(){
            object NavigateToCatalog: Command()
            class ShowError(val message: String): Command()
            object SetDate: Command()
        }
    }


}