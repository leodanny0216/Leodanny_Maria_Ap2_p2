package edu.ucne.leodanny_maria_ap2_p2.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.leodanny_maria_ap2_p2.presentation.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen,
        modifier = modifier
    ) {
        composable<Screen.HomeScreen> {
            HomeScreen()
        }
    }
}
