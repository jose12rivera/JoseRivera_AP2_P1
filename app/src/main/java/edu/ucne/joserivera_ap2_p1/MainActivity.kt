package edu.ucne.joserivera_ap2_p1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.room.Room
import edu.ucne.joserivera_ap2_p1.data.local.database.TareaDb
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import edu.ucne.joserivera_ap2_p1.data.respository.TareaRespository
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaListScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaviewModel
import edu.ucne.joserivera_ap2_p1.ui.theme.JoseRivera_AP2_P1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tareaDb = Room.databaseBuilder(
            applicationContext,
            TareaDb::class.java,
            "Tarea.db"
        ).fallbackToDestructiveMigration().build()

        val repository = TareaRespository(tareaDb.Tareadao())

        setContent {
            JoseRivera_AP2_P1Theme {
                val viewModel = remember { TareaviewModel(repository) }
                var mostrarFormulario by remember { mutableStateOf(false) }
                var tareaSeleccionada by remember { mutableStateOf<TareaEntity?>(null) }

                if (mostrarFormulario) {
                    TareaScreen(
                        tarea = tareaSeleccionada ?: TareaEntity(),
                        onGuardar = { descripcion, tiempo, id ->
                            viewModel.Agregartarea(descripcion, tiempo,id)
                            mostrarFormulario = false
                        },
                        onCancelar = { mostrarFormulario = false }
                    )
                } else {
                    val lista by viewModel.tarealist.collectAsState()
                    TareaListScreen(
                        tarealist = lista,
                        onCreate = {
                            tareaSeleccionada = null
                            mostrarFormulario = true
                        },
                        onDelete = { viewModel.tareadelete(it) },
                        onEditar = {
                            tareaSeleccionada = it
                            mostrarFormulario = true
                        }
                    )
                }
            }
        }
    }
}
