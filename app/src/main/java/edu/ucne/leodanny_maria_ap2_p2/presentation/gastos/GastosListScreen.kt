package edu.ucne.leodanny_maria_ap2_p2.presentation.gastos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.ucne.leodanny_maria_ap2_p2.presentation.Navigation.Screen
import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosListScreen(
    navController: NavController,
    viewModel: GastosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var gastoToDelete by remember { mutableStateOf<GastoDto?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadGastos()
    }


    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                gastoToDelete = null
            },
            title = { Text("Eliminar Gasto") },
            text = { Text("¿Estás seguro de que quieres eliminar este gasto? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        gastoToDelete?.let { gasto ->
                            viewModel.deleteGasto(gasto.gastoId)
                        }
                        showDeleteDialog = false
                        gastoToDelete = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        gastoToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Lista de Gastos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.GastosScreen.route) },
                containerColor = Color(0xFF0D47A1),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Agregar Gasto")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF0D47A1))
                    }
                }
                uiState.errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Error: ${uiState.errorMessage}",
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadGastos() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                            ) {
                                Text("Reintentar", color = Color.White)
                            }
                        }
                    }
                }
                uiState.gastos.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay gastos registrados", color = Color.Gray)
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.gastos) { gasto ->
                            GastosListItem(
                                gasto = gasto,
                                onEditClick = {
                                    navController.navigate("${Screen.GastosScreen.route}?gastoId=${gasto.gastoId}")
                                },
                                onDeleteClick = {
                                    gastoToDelete = gasto
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun GastosListItem(
    gasto: GastoDto,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Suplidor: ${gasto.suplidor ?: "No especificado"}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("NCF: ${gasto.ncf ?: "N/A"}", color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text("ITBIS: ${gasto.itbis}", color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Monto: $${String.format("%.2f", gasto.monto)}", color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Fecha: ${gasto.fecha ?: "No especificada"}", color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF0D47A1))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}
