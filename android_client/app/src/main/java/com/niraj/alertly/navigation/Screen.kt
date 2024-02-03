package com.niraj.alertly.navigation

import com.niraj.alertly.utils.Constants.GROUP_SCREEN_ARGUMENT_KEY

sealed class Screen(val route : String){

    object Login: Screen(route = "login_screen")
    object Home: Screen(route = "home_screen")

    object Group: Screen(route = "group_screen?${GROUP_SCREEN_ARGUMENT_KEY}={$GROUP_SCREEN_ARGUMENT_KEY}") {
        fun passGroupId(groupID: Int) = "group_screen?$GROUP_SCREEN_ARGUMENT_KEY=$groupID"
    }

//    object Authentication: Screen(route = "authentication_screen")

//    object Write : Screen(route = "write_screen?$WRITE_SCREEN_ARGUMENT_KEY=" + "{$WRITE_SCREEN_ARGUMENT_KEY}") {
//        fun passDiaryId(diaryId: String) =
//            "write_screen?$WRITE_SCREEN_ARGUMENT_KEY=$diaryId"
//    }
}