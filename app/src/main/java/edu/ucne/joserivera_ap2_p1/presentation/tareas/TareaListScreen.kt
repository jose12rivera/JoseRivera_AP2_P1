package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaListScreen(
    viewModel: TareasViewModel = hiltViewModel(),
    goToTarea: (Int) -> Unit,
    createTarea: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Lista de Tareas",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6650a4)),
                actions = {
                    IconButton(onClick = createTarea) {
                        Icon(Icons.Default.Add, contentDescription = "Nueva tarea", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE9E7EF), Color(0xFF6650a4))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (uiState.tareas.isEmpty()) {
                Text("No hay tareas registradas.", color = Color.Gray)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.tareas) { tarea ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(tarea.descripcion, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text("Tiempo: ${tarea.tiempo} hrs", fontSize = 14.sp)
                                }

                                Row {
                                    IconButton(onClick = { goToTarea(tarea.tareaId) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                                    }

                                    IconButton(onClick = {
                                        viewModel.onEvent(TareaEvent.DeleteById(tarea.tareaId))
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
