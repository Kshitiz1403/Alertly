package com.niraj.alertly.data.groupalerts

data class GetGroupAlertsResponse(
    val `data`: List<Alert> = emptyList(),
    val success: Boolean = false
)

data class Alert(
    val alert_id: Int,
    val description: String,
    val group_id: Int,
    val message_sender_id: String,
    val sender_name: String,
    val sent_at: String,
    val severity: String,
    val title: String
)