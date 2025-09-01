package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaScreen(
    tarea: TareaEntity,
    viewModel: TareaviewModel,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    var descripcion by remember { mutableStateOf(tarea.descripcion) }
    var tiempo by remember { mutableStateOf(tarea.tiempo.toString()) }

    var errores by remember { mutableStateOf<Map<String,String>>(emptyMap()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registrar Tarea", fontSize = 20.sp, color = Color.White) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6650a4), Color(0xFF9370DB))
                    )
                )
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                isError = errores.containsKey("descripcion"),
                modifier = Modifier.fillMaxWidth()
            )
            errores["descripcion"]?.let {
                Text(text = it, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tiempo,
                onValueChange = { tiempo = it },
                label = { Text("Tiempo (min)") },
                isError = errores.containsKey("tiempo"),
                modifier = Modifier.fillMaxWidth()
            )
            errores["tiempo"]?.let {
                Text(text = it, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        val (valido, errs) = viewModel.validarTarea(descripcion, tiempo)
                        errores = errs
                        if (valido) {
                            viewModel.agregarTarea(descripcion, tiempo.toInt(), tarea.tareaid)
                            onGuardar()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Green.copy(alpha = 0.2f),
                        contentColor = Color.Green
                    )
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Guardar")
                }

                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Blue.copy(alpha = 0.2f),
                        contentColor = Color.Blue
                    )
                ) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cancelar")
                }
            }
        }
    }
}
