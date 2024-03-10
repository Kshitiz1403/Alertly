package com.niraj.alertly.network

import com.niraj.alertly.BuildConfig
import com.niraj.alertly.data.login.LoginRequest
import com.niraj.alertly.data.login.LoginResponse
import com.niraj.alertly.data.MyGroupResponse
import com.niraj.alertly.data.createalert.CreateAlertRequest
import com.niraj.alertly.data.createalert.CreateAlertResponse
import com.niraj.alertly.data.creategroup.CreateGroupRequest
import com.niraj.alertly.data.creategroup.CreateGroupResponse
import com.niraj.alertly.data.getaccesstoken.GetAccessTokenResponse
import com.niraj.alertly.data.groupalerts.GetGroupAlertsResponse
import com.niraj.alertly.data.joingroup.JoinGroupRequest
import com.niraj.alertly.data.joingroup.JoinGroupResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AlertlyAPI {

    @POST("api/auth/google/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/groups")
    suspend fun getGroups(@Header("Authorization") bearerToken :String): Response<MyGroupResponse>

    @POST("api/groups/create")
    suspend fun createGroup(@Header("Authorization") bearerToken :String, @Body createGroupRequest: CreateGroupRequest): Response<CreateGroupResponse>

    @POST("api/groups/join")
    suspend fun joinGroup(@Header("Authorization") bearerToken: String, @Body joinGroupRequest: JoinGroupRequest): Response<JoinGroupResponse>

    @GET("api/groups/{groupId}")
    suspend fun getGroupAlerts(
        @Header("Authorization") bearerToken: String,
        @Path("groupId") groupID: Int,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int
    ): Response<GetGroupAlertsResponse>

    @POST("api/groups/{groupId}/alert")
    suspend fun createAlert(
        @Header("Authorization") bearerToken: String,
        @Path("groupId") groupID: Int,
        @Body createAlertRequest: CreateAlertRequest
    ) : Response<CreateAlertResponse>

    @GET("api/groups/{groupId}/access_token")
    suspend fun getAccessToken(
        @Header("Authorization") bearerToken: String,
        @Path("groupId") groupId: Int
    ): Response<GetAccessTokenResponse>

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