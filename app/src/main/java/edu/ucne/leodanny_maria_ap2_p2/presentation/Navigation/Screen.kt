package edu.ucne.leodanny_maria_ap2_p2.presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()
}
