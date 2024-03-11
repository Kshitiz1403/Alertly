package com.niraj.alertly.utils

import androidx.compose.ui.graphics.Color

val containerColorMap: Map<String, Color> = mapOf(
    Pair("Normal", Color(0xFFCBFFA9)),
    Pair("Elevated", Color(0xFFFFFEC4)),
    Pair("Danger", Color(0xFFFF9B9B)),
)
val contentColorMap: Map<String, Color> = mapOf(
    Pair("Normal", Color.Black),
    Pair("Elevated", Color.Black),
    Pair("Danger", Color.White),
)