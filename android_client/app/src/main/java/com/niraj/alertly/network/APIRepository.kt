package com.niraj.alertly.network

import android.util.Log
import com.niraj.alertly.data.LoginRequest
class APIRepository {
    private val alertlyAPI = AlertlyAPI.getInstance()
    suspend fun Login(token: String) {
        val body = LoginRequest(id_token = token)
        val resp = alertlyAPI.login(body)
    }
}