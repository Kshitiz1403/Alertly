package com.niraj.alertly.DI

import com.niraj.alertly.data.Data
import com.niraj.alertly.data.LoginRequest
import com.niraj.alertly.data.LoginResponse
import com.niraj.alertly.network.AlertlyAPI
import javax.inject.Inject

class APIRepository @Inject constructor(
//    private val alertlyAPI: AlertlyAPI
) {
    private val alertlyAPI = AlertlyAPI.getInstance();
    suspend fun Login(token: String): LoginResponse {
        val body = LoginRequest(id_token = token)
        val resp = alertlyAPI.login(body)
        if(resp.isSuccessful && resp.body() != null) {
            return resp.body()!!
        }
        val errorResp = LoginResponse(success = false, data = Data())
        return errorResp
    }
}