package com.niraj.alertly.presentation.screens.LoginScreen

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import com.niraj.alertly.DI.APIRepository
import com.niraj.alertly.data.Data
import com.niraj.alertly.data.LoginResponse
import com.niraj.alertly.data.LoginState
import com.niraj.alertly.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    val apiRepository = APIRepository()
    private val _loginState = MutableStateFlow<LoginState>(LoginState.NOTLOGGEDIN)
    val loginState = _loginState.asStateFlow()

    private var token: String = "-1"
    suspend fun Login(token: String) {
        _loginState.emit(LoginState.PROGRESS)
        val resp = apiRepository.Login(token)
        if(!resp.success) {
            _loginState.emit(LoginState.FAILED)
            /* TODO Notify user of not correctly logging in*/
            delay(2000)
            _loginState.emit(LoginState.NOTLOGGEDIN)
        } else  {
            Log.d("NIRAJ", resp.toString())
            this.token = resp.data.token
            _loginState.emit(LoginState.SUCCESS)
        }
    }

    suspend fun saveToken(ctx: Context){
        val prefKey = stringPreferencesKey("token")
        ctx.dataStore.edit {
            it[prefKey] = token
        }
    }
}