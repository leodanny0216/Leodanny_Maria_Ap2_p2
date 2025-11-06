package edu.ucne.leodanny_maria_ap2_p2.presentation.Navigation

// Screen.kt (actualizado)
sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object GastosListScreen : Screen("gastos_list_screen")
    object GastosScreen : Screen("gastos_screen") {
        fun createRoute(gastoId: Int? = null): String {
            return if (gastoId != null) {
                "gastos_screen?gastoId=$gastoId"
            } else {
                "gastos_screen"
            }
        }
    }
}
