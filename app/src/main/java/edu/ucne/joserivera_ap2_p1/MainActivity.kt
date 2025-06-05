package edu.ucne.joserivera_ap2_p1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

class MainActivity : ComponentActivity() {   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       val TareaDb= Room.databaseBuilder(
           applicationContext,
           TareaDb::class.java,
           "Tarea.Db"
       ).fallbackToDestructiveMigration().build()

    val respository= TareaRespository(TareaDb.Tareadao())

        setContent {
            JoseRivera_AP2_P1Theme {
              val tareaviewModel: TareaviewModel= remember {
                  TareaviewModel(respository)
              }

                var mostrarFormulario by remember { mutableStateOf(false) }
                var selecionartarea by remember { mutableStateOf(TareaEntity(0,"",0.0)) }

                if (mostrarFormulario){
                    TareaScreen(
                       tarea=selecionartarea,
                        OnAgregar = {descripcion,tiempo->
                            tareaviewModel.Agregartarea(descripcion,tiempo)
                        }, Oncancelar={
                            mostrarFormulario =false
                        }
                    )
                }else{
                    TareaListScreen(
                       tarealist=tareaviewModel.tarealist.collectAsState().value,
                        Oncreate ={
                            selecionartarea= TareaEntity(0,"",0.0)
                            mostrarFormulario =true
                        },OnDelete={tarea->
                            tareaviewModel.tareadelete(tarea)
                        },OnEditar={tarea->
                            selecionartarea= tarea
                            mostrarFormulario =true
                        }


                    )
                }
            }
        }
    }
}

