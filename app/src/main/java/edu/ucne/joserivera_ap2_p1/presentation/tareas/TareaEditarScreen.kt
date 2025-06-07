package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaEditarScreen(
    tareaId: Int,
    viewModel: TareaviewModel,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    // Cargar la tarea cuando el screen se muestra o cambia el tareaId
    LaunchedEffect(tareaId) {
        if (tareaId > 0) {
            viewModel.loadTarea(tareaId)
        } else {
            viewModel.onEvent(TareaEvent.DescripcionChange(""))
            viewModel.onEvent(TareaEvent.TiempoChange(""))
        }
    }

    // Observar los cambios en el estado
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (tareaId > 0) "Editar Tarea" else "Nueva Tarea",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Cancelar",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TareaEvent.Save)
                            if (uiState.errorMessage == null) {
                                onGuardar()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Guardar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = {
                    viewModel.onEvent(TareaEvent.DescripcionChange(it))
                },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMessage != null && uiState.descripcion.isBlank()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.tiempo,
                onValueChange = {
                    viewModel.onEvent(TareaEvent.TiempoChange(it))
                },
                label = { Text("Tiempo (minutos)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.errorMessage != null &&
                        (uiState.tiempo.isBlank() || uiState.tiempo.toIntOrNull() == null)
            )

            // Mostrar mensaje de error si existe
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}