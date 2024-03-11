package com.niraj.alertly.navigation

import com.niraj.alertly.utils.Constants.ALERT_DESCRIPTION
import com.niraj.alertly.utils.Constants.ALERT_TITLE
import com.niraj.alertly.utils.Constants.GROUP_SCREEN_ARGUMENT_KEY

sealed class Screen(val route : String){

    object Login: Screen(route = "login_screen")
    object Home: Screen(route = "home_screen")

    object Group: Screen(route = "group_screen?${GROUP_SCREEN_ARGUMENT_KEY}={$GROUP_SCREEN_ARGUMENT_KEY}") {
        fun passGroupId(groupID: Int) = "group_screen?$GROUP_SCREEN_ARGUMENT_KEY=$groupID"
    }

    object Alert: Screen(route = "alert_screen")
}