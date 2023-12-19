package com.niraj.alertly.presentation.screens.LoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.niraj.alertly.DI.APIRepository
import com.niraj.alertly.data.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepository: APIRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.NOTLOGGEDIN)
    val loginState = _loginState.asStateFlow()

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
            _loginState.emit(LoginState.SUCCESS)
        }
    }
}