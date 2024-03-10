package com.niraj.alertly.data.createalert

data class CreateAlertRequest(
    val description: String,
    val severity: String,
    val title: String
)