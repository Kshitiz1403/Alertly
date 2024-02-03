package com.niraj.alertly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.niraj.alertly.presentation.screens.groupscreen.GroupScreen
import com.niraj.alertly.presentation.screens.homescreen.HomeScreen
import com.niraj.alertly.presentation.screens.loginscreen.LoginScreen
import com.niraj.alertly.utils.Constants.GROUP_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
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
        val groupId = it.arguments!!.getInt("groupId")
        GroupScreen(
            groupId = groupId,
            navigateToHome = navigateToHome
        )
    }
}