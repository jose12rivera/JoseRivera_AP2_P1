package edu.ucne.joserivera_ap2_p1.presentation.tareas

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
    OnAgregar:(String, Double)-> Unit,
    Oncancelar:()-> Unit
){
    var descripcion: String by remember { mutableStateOf(tarea.descripcion?:"") }
    var tiempo: String by remember { mutableStateOf(tarea.tiempo.toString()?:"") }

    Scaffold { innerpadding ->
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(innerpadding)
                .padding(16.dp)
        ){
            ElevatedCard (
                modifier = Modifier.fillMaxWidth()
            ){
                OutlinedTextField(
                    label = { Text(text = "descripcion") },
                    value = descripcion,
                    onValueChange = {descripcion=it},
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text(text = "tiempo") },
                    value = tiempo,
                    onValueChange = {tiempo= it},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Row (modifier = Modifier.fillMaxWidth()){
                    OutlinedButton(
                        onClick = {
                            val tiempovalue=tiempo.toDoubleOrNull()?:0.0
                            OnAgregar(descripcion,tiempovalue)
                        }
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "save button")
                        Text(text ="Guardar" )
                    }
                    val scope=rememberCoroutineScope()
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cancelar button")
                        Text(text ="Cancelar" )
                    }

                }
            }
        }
    }
}