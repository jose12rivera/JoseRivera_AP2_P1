package edu.ucne.joserivera_ap2_p1.presentation.tareas

import TareaEvent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaBodyScreen(
    uiState: TareaUiState,
    onEvent: (TareaEvent) -> Unit,
    goBack: () -> Unit
) {
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.tareaId != null && uiState.tareaId > 0)
                            "Editar Tarea"
                        else
                            "Nueva Tarea"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Default.Close, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val tiempoInt = uiState.tiempo.toIntOrNull()
                        if (uiState.descripcion.isNotBlank() && tiempoInt != null && tiempoInt > 0) {
                            onEvent(TareaEvent.Save)
                            goBack()
                        } else {
                            showError = true
                        }
                    }) {
                        Icon(Icons.Default.Done, contentDescription = "Guardar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = { onEvent(TareaEvent.DescripcionChange(it)) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && uiState.descripcion.isBlank()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.tiempo,
                onValueChange = { onEvent(TareaEvent.TiempoChange(it)) },
                label = { Text("Tiempo (minutos)") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && (uiState.tiempo.isBlank() || uiState.tiempo.toIntOrNull() == null)
            )

            if (showError && (uiState.descripcion.isBlank() || uiState.tiempo.toIntOrNull() == null)) {
                Text(
                    text = "Por favor complete todos los campos correctamente.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
