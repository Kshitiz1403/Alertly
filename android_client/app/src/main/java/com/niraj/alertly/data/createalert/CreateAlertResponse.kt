package com.niraj.alertly.data.createalert

data class CreateAlertResponse(
    val `data`: CreateAlertData = CreateAlertData(),
    val success: Boolean = false
)

data class CreateAlertData(
    val alert_id: Int = 0,
    val description: String = "",
    val family_name: String = "",
    val given_name: String = "",
    val sent_at: String = "",
    val severity: String = "",
    val title: String = "",
    val user_id: String = ""
)