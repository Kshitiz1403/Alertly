package com.niraj.alertly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.niraj.alertly.presentation.screens.HomeScreen.HomeScreen
import com.niraj.alertly.presentation.screens.LoginScreen.LoginScreen

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(navController = navController, startDestination = startDestination ) {
        loginRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeScreenRoute()
    }
}

fun NavGraphBuilder.homeScreenRoute(

) {
    composable(route = Screen.Home.route) {
        HomeScreen()
    }
}

fun NavGraphBuilder.loginRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Screen.Login.route) {
        LoginScreen(navigateToHome = navigateToHome)
    }
}

