package com.niraj.alertly.data

import com.google.gson.annotations.SerializedName

data class MyGroupResponse(
    val `data`: List<GroupData> = emptyList(),
    val success: Boolean = false
)

data class GroupData(
    val created_at: String = "",
    val description: String = "",
    val group_id: Int = 3,
    val group_name: String = "Loading",
    val is_admin: Boolean = false,
    val pinned: Boolean = false
)