package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

@Composable
fun TareaListScreen(
    tarealist: List<TareaEntity>,
    onCreate: () -> Unit,
    onDelete: (TareaEntity) -> Unit,
    onEditar: (TareaEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate,
                containerColor = Color(0xFF6650a4),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF6650a4), Color(0xFF6650a4)))
                )
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Lista de tareas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tarealist) { tarea ->
                    TareaRow(tarea, onDelete, onEditar)
                }
            }
        }
    }
}

@Composable
fun TareaRow(
    tarea: TareaEntity,
    onDelete: (TareaEntity) -> Unit,
    onEditar: (TareaEntity) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Descripción: ${tarea.descripcion}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Tiempo: ${tarea.tiempo} minutos", fontSize = 14.sp)
            }
            Row {
                IconButton(onClick = { onEditar(tarea) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { onDelete(tarea) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
