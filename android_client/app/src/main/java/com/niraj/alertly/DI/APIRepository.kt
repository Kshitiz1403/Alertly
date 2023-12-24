package com.niraj.alertly.DI

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.niraj.alertly.MyApplication
import com.niraj.alertly.data.login.Data
import com.niraj.alertly.data.login.LoginRequest
import com.niraj.alertly.data.login.LoginResponse
import com.niraj.alertly.data.MyGroupResponse
import com.niraj.alertly.data.creategroup.CreateGroupRequest
import com.niraj.alertly.data.creategroup.CreateGroupResponse
import com.niraj.alertly.data.groupalerts.GetGroupAlertsResponse
import com.niraj.alertly.data.joingroup.JoinGroupRequest
import com.niraj.alertly.data.joingroup.JoinGroupResponse
import com.niraj.alertly.dataStore
import com.niraj.alertly.network.AlertlyAPI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class APIRepository @Inject constructor(

) {
    private val alertlyAPI = AlertlyAPI.getInstance();
    private var token = "-1"
    suspend fun Login(token: String): LoginResponse {
        val body = LoginRequest(id_token = token)
        val resp = alertlyAPI.login(body)
        if(resp.isSuccessful && resp.body() != null) {
            return resp.body()!!
        }
        val errorResp = LoginResponse(success = false, data = Data())
        return errorResp
    }

    suspend fun getGroups(): MyGroupResponse {
        val authToken = "Bearer ${getToken(MyApplication.appContext)}"
        val resp = alertlyAPI.getGroups(authToken)
        if (resp.isSuccessful && resp.body() != null) {
            return resp.body()!!
        }
        return MyGroupResponse()
    }

    suspend fun joinGroup(accessToken: String): JoinGroupResponse {
        val authToken = "Bearer ${getToken(MyApplication.appContext)}"
        val reqBody = JoinGroupRequest(accessToken)
        val resp = alertlyAPI.joinGroup(authToken, reqBody)
        if (resp.isSuccessful && resp.body() != null) {
            return resp.body()!!
        }
        return JoinGroupResponse()
    }

    suspend fun createGroup(groupName: String, groupDescription: String): CreateGroupResponse {
        val authToken = "Bearer ${getToken(MyApplication.appContext)}"
        val createGroupRequest = CreateGroupRequest(groupName, groupDescription)
        val resp = alertlyAPI.createGroup(authToken, createGroupRequest)

        if (resp.isSuccessful && resp.body() != null) {
            return resp.body()!!
        }
        return CreateGroupResponse()
    }

    suspend fun getGroupAlerts(
        groupId: Int,
        pageNumber: Int,
        pageSize: Int
    ): GetGroupAlertsResponse {
        val authToken = "Bearer ${getToken(MyApplication.appContext)}"
        Log.d("NIRAJ22", "Calling Alerts")

        val resp = alertlyAPI.getGroupAlerts(authToken, groupId, pageNumber, pageSize)
        Log.d("NIRAJ22", resp.body().toString())
        if(resp.isSuccessful && resp.body() != null) {
            return resp.body()!!
        }
        return GetGroupAlertsResponse()
    }

    fun getToken(ctx: Context): String {
        if(token != "-1") return token
        return runBlocking {
            val prefKey = stringPreferencesKey("token")
            val preference = ctx.dataStore.data.first()
            token = preference[prefKey] ?: "-1"
            token
        }
    }
}