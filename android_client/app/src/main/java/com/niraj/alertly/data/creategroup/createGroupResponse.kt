package com.niraj.alertly.data.creategroup

data class CreateGroupResponse(
    val `data`: CreateGroupData = CreateGroupData(),
    val success: Boolean = false
)

data class CreateGroupData(
    val description: String = "",
    val groupID: Int = -1,
    val group_name: String = ""
)

data class CreateGroupRequest(
    val group_name: String,
    val description: String
)