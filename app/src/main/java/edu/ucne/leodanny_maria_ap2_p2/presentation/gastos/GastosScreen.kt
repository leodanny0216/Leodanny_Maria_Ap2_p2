package edu.ucne.leodanny_maria_ap2_p2.presentation.gastos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosScreen(
    navController: NavController,
    viewModel: GastosViewModel = hiltViewModel(),
    gastoId: Int? = null
) {
    var suplidor by remember { mutableStateOf("") }
    var ncf by remember { mutableStateOf("") }
    var itbis by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    // Cargar gasto existente (modo edición)
    LaunchedEffect(gastoId) {
        if (gastoId != null && gastoId != 0) {
            coroutineScope.launch {
                val gasto = viewModel.getGastoById(gastoId)
                gasto?.let {
                    suplidor = it.suplidor ?: ""
                    ncf = it.ncf ?: ""
                    itbis = it.itbis.toString()
                    monto = it.monto.toString()
                    fecha = it.fecha ?: ""
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (gastoId != null && gastoId != 0) "Editar Gasto" else "Registrar Gasto",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D47A1))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = suplidor,
                onValueChange = { suplidor = it },
                label = { Text("Suplidor") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = ncf,
                onValueChange = { ncf = it },
                label = { Text("NCF") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = itbis,
                onValueChange = { itbis = it },
                label = { Text("ITBIS") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    if (suplidor.isNotBlank() && monto.isNotBlank() && fecha.isNotBlank()) {
                        viewModel.saveGasto(
                            GastoDto(
                                gastoId = gastoId ?: 0,
                                suplidor = suplidor,
                                ncf = ncf,
                                itbis = itbis.toDoubleOrNull() ?: 0.0,
                                monto = monto.toDoubleOrNull() ?: 0.0,
                                fecha = fecha
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
            ) {
                Text(
                    if (gastoId != null && gastoId != 0) "Actualizar" else "Guardar",
                    color = Color.White
                )
            }

            if (gastoId != null && gastoId != 0) {
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        }
    }
}
