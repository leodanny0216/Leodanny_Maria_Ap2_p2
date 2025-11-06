package edu.ucne.leodanny_maria_ap2_p2.presentation.gastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.leodanny_maria_ap2_p2.data.remote.Resource
import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto
import edu.ucne.leodanny_maria_ap2_p2.data.respository.GastosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GastosViewModel @Inject constructor(
    private val repository: GastosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GastosUiState())
    val uiState: StateFlow<GastosUiState> = _uiState.asStateFlow()

    init {
        loadGastos()
    }

    fun loadGastos() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            repository.getGastos().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            gastos = resource.data ?: emptyList(),
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = resource.message
                        )
                    }
                }
            }
        }
    }

    suspend fun getGastoById(id: Int): GastoDto? {
        return repository.getGasto(id)
    }

    fun deleteGasto(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val success = repository.deleteGasto(id)
                if (success) {
                    // Actualizar la lista localmente sin recargar toda la lista
                    val currentGastos = _uiState.value.gastos.toMutableList()
                    currentGastos.removeAll { it.gastoId == id }
                    _uiState.value = _uiState.value.copy(
                        gastos = currentGastos,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo eliminar el gasto"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al eliminar: ${e.message}"
                )
            }
        }
    }

    fun saveGasto(gasto: GastoDto) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val success = if (gasto.gastoId == 0) {
                    repository.createGasto(gasto)
                } else {
                    repository.updateGasto(gasto.gastoId, gasto)
                }

                if (success) {
                    loadGastos() // Recargar la lista después de guardar
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo guardar el gasto"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al guardar: ${e.message}"
                )
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}