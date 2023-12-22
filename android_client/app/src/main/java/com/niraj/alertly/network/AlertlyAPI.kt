package com.niraj.alertly.network

import com.niraj.alertly.BuildConfig
import com.niraj.alertly.data.LoginRequest
import com.niraj.alertly.data.LoginResponse
import com.niraj.alertly.data.MyGroupResponse
import com.niraj.alertly.data.creategroup.CreateGroupRequest
import com.niraj.alertly.data.creategroup.CreateGroupResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AlertlyAPI {

    @POST("api/auth/google/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/groups")
    suspend fun getGroups(@Header("Authorization") bearerToken :String): Response<MyGroupResponse>

    @POST("api/groups/create")
    suspend fun createGroup(@Header("Authorization") bearerToken :String, @Body createGroupRequest: CreateGroupRequest): Response<CreateGroupResponse>

    companion object {
        private var apiService: AlertlyAPI? = null
        fun getInstance() : AlertlyAPI {
            if(apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(AlertlyAPI::class.java)
            }
            return apiService!!
        }
    }
}