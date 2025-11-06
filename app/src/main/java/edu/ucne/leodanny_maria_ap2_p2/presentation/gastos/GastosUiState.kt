package edu.ucne.leodanny_maria_ap2_p2.presentation.gastos

import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto

data class GastosUiState(
    val gastos: List<GastoDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)