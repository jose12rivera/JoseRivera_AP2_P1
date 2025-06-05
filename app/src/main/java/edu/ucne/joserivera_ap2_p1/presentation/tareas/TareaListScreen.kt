package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
    tarealist:List<TareaEntity>,
    Oncreate:()-> Unit,
    OnDelete:(TareaEntity)-> Unit,
    OnEditar:(TareaEntity)-> Unit
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = Oncreate,
                containerColor = Color(0xFF6650a4),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ){ PaddingValues->
        Column (
            modifier = Modifier.fillMaxSize().
            background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6650a4), Color(0xFF6650a4))
                )
            ).padding(PaddingValues).padding(16.dp)
        ){
            Text(text = "Lista de tareas",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center

                ),
                modifier = Modifier.fillMaxSize()
            )

            LazyColumn (verticalArrangement = Arrangement.Center){
                items (tarealist){tarea->
                    TareaRow(tarea,OnEditar,OnDelete)
                }
            }
        }
    }

}

@Composable
fun TareaRow(
    tarea: TareaEntity,
    OnDelete:(TareaEntity)-> Unit,
    OnEditar:(TareaEntity)-> Unit
){
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxSize()
    ){
        Row (modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = "descripcion:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = tarea.descripcion,  fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = "tiempo:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = tarea.tiempo.toString(),  fontSize = 16.sp)
                }
            }

            Row {
                FloatingActionButton(onClick = {OnEditar(tarea)}) {
                    Icon(Icons.Default.Edit, contentDescription = "editar")
                }
                FloatingActionButton(onClick = {OnDelete(tarea)}) {
                    Icon(Icons.Default.Delete, contentDescription = "eliminar")
                }
            }
        }
    }
}

