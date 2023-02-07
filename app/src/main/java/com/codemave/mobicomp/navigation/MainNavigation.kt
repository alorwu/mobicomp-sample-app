package com.codemave.mobicomp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codemave.mobicomp.ui.HomeScreen
import com.codemave.mobicomp.ui.login.LoginScreen
import com.codemave.mobicomp.ui.payment.PaymentScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("payments") {
                PaymentScreen(navController = navController)
        }
    }
}