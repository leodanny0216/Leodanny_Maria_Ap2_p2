package edu.ucne.leodanny_maria_ap2_p2.presentation.gastos

import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto

data class GastosUiState(
    val isLoading: Boolean = false,
    val gastos: List<GastoDto> = emptyList(),
    val errorMessage: String? = null
)