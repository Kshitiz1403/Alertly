package com.niraj.alertly.data.login

data class LoginResponse(
    val `data`: Data,
    val success: Boolean
)
data class Data(
    val email: String = "",
    val sub: String = "",
    val token: String = ""
)