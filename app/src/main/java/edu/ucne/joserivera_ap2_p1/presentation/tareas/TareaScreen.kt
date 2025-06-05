package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

@Composable
fun TareaScreen(
    tarea: TareaEntity,
    onGuardar: (String, Int, Int?) -> Unit,
    onCancelar: () -> Unit
) {
    var descripcion by remember { mutableStateOf(tarea.descripcion) }
    var tiempo by remember { mutableStateOf(tarea.tiempo.toString()) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    label = { Text("Descripción") },
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text("Tiempo") },
                    value = tiempo,
                    onValueChange = { tiempo = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    OutlinedButton(onClick = {
                        onGuardar(descripcion, tiempo.toIntOrNull() ?: 0, tarea.tareaid)
                    }) {
                        Icon(Icons.Default.Done, contentDescription = null)
                        Text("Guardar")
                    }
                    OutlinedButton(onClick = onCancelar) {
                        Icon(Icons.Default.Close, contentDescription = null)
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}
