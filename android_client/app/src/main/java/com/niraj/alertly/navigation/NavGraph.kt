package com.niraj.alertly.navigation

import android.content.ClipDescription
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.niraj.alertly.deleteToken
import com.niraj.alertly.presentation.screens.alertscreen.AlertScreen
import com.niraj.alertly.presentation.screens.groupscreen.GroupScreen
import com.niraj.alertly.presentation.screens.homescreen.HomeScreen
import com.niraj.alertly.presentation.screens.loginscreen.LoginScreen
import com.niraj.alertly.utils.Constants.ALERT_DESCRIPTION
import com.niraj.alertly.utils.Constants.ALERT_SEVERITY
import com.niraj.alertly.utils.Constants.ALERT_TITLE
import com.niraj.alertly.utils.Constants.GROUP_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController, intent: Intent) {
    val ctx = LocalContext.current
    var alert = ""
    var description = ""
    var severity = ""
    if(startDestination == Screen.Alert.route) {
        alert = intent.getStringExtra(ALERT_TITLE)!!
        description = intent.getStringExtra(ALERT_DESCRIPTION)!!
        severity = intent.getStringExtra(ALERT_SEVERITY)!!
    }
    NavHost(navController = navController, startDestination = startDestination ) {
        loginRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeScreenRoute(
            navigateToLogin = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
                deleteToken(ctx = ctx)
            },
            navigateToGroup = {
                navController.navigate(Screen.Group.passGroupId(it))
            }
        )
        groupScreenRoute(
            navigateToHome = {
                navController.popBackStack()
            }
        )
        alertScreenRoute(
            alert = alert,
            description = description,
            severity = severity,
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
    }
}

fun NavGraphBuilder.homeScreenRoute(
    navigateToLogin: () -> Unit,
    navigateToGroup: (Int) -> Unit
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            navigateToLogin = navigateToLogin,
            navigateToGroup = navigateToGroup
        )
    }
}

fun NavGraphBuilder.loginRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Screen.Login.route) {
        LoginScreen(
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.groupScreenRoute(
    navigateToHome: () -> Unit
) {
    composable(
        route = Screen.Group.route,
        arguments = listOf(
            navArgument(GROUP_SCREEN_ARGUMENT_KEY) {
                type = NavType.IntType
                nullable = false
                defaultValue = 0
            }
        )
    ) {
        val groupId = it.arguments!!.getInt("groupID")
        GroupScreen(
            groupId = groupId,
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.alertScreenRoute(
    alert: String,
    description: String,
    severity: String,
    navigateToHome: () -> Unit
) {
    composable(
        route = Screen.Alert.route
    ){
        AlertScreen(
            alert = alert,
            description = description,
            severity = severity,
            navigateToHome = navigateToHome
        )
    }
}