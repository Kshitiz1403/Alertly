package com.niraj.alertly.data

import com.google.gson.annotations.SerializedName

data class MyGroupResponse(
    val `data`: List<GroupData> ,
    val success: Boolean
)

data class GroupData(
    val created_at: String,
    val description: String,
    val group_id: Int,
    val group_name: String,
    val is_admin: Boolean,
    val pinned: Boolean
)