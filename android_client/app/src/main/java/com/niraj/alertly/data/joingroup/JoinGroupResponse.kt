package com.niraj.alertly.data.joingroup

data class JoinGroupResponse(
    val success: Boolean = false,
    val data: Any = ""
)

data class JoinGroupRequest(
    val access_token: String
)