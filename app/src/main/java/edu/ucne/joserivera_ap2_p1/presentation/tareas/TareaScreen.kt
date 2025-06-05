package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

@Composable
fun TareaScreen(
    tarea: TareaEntity,
    onGuardar: (String, Int, Int?) -> Unit,
    onCancelar: () -> Unit
) {
    var descripcion by remember { mutableStateOf(tarea.descripcion) }
    var tiempo by remember { mutableStateOf(tarea.tiempo.toString()) }
    var showError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onCancelar) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Registrar Tarea",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && descripcion.isBlank()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = tiempo,
                    onValueChange = { tiempo = it },
                    label = { Text("Tiempo (minutos)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && (tiempo.isBlank() || tiempo.toIntOrNull() == null)
                )

                if (showError && (descripcion.isBlank() || tiempo.isBlank() || tiempo.toIntOrNull() == null)) {
                    Text(
                        text = "Por favor complete todos los campos correctamente",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancelar) {
                        Icon(Icons.Default.Close, contentDescription = "Cancelar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (descripcion.isNotBlank() && tiempo.toIntOrNull() != null) {
                                onGuardar(descripcion, tiempo.toInt(), tarea.tareaid)
                            } else {
                                showError = true
                            }
                        }
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Guardar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Guardar")
                    }
                }
            }
        }
    }
}