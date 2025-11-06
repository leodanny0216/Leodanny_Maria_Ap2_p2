package edu.ucne.leodanny_maria_ap2_p2.presentation.Navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.leodanny_maria_ap2_p2.presentation.HomeScreen
import edu.ucne.leodanny_maria_ap2_p2.presentation.gastos.GastosListScreen
import edu.ucne.leodanny_maria_ap2_p2.presentation.gastos.GastosScreen


// AppNavigation.kt (actualizado)
@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.GastosListScreen.route) {
            GastosListScreen(navController)
        }
        composable(
            route = "${Screen.GastosScreen.route}?gastoId={gastoId}",
            arguments = listOf(
                navArgument("gastoId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val gastoId = backStackEntry.arguments?.getInt("gastoId") ?: 0
            GastosScreen(
                navController = navController,
                gastoId = if (gastoId == 0) null else gastoId
            )
        }
    }
}