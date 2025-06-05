package edu.ucne.joserivera_ap2_p1.presentation.navigation


import TareaListScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import edu.ucne.joserivera_ap2_p1.presentation.HomeScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.DeleteTareaScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaEditarScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaviewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: TareaviewModel
) {
    val tarealist by viewModel.tarealist.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("tarea_list") {
            TareaListScreen(
                tarealist = tarealist,
                onCreate = { navController.navigate("tarea_nueva") },
                onDelete = { tareaId -> navController.navigate("eliminar_tarea/$tareaId") },
                onEditar = { navController.navigate("editar_tarea/${it.tareaid}") }
            )
        }


        composable("tarea_nueva") {
            TareaScreen(
                tarea = TareaEntity(),
                onGuardar = { descripcion, tiempo, _ ->
                    viewModel.agregarTarea(descripcion, tiempo)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable("editar_tarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
            TareaEditarScreen(
                tareaId = tareaId,
                viewModel = viewModel,
                onGuardar = { navController.popBackStack() },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable("eliminar_tarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
            DeleteTareaScreen(
                viewModel = viewModel,
                tareaId = tareaId,
                goBack = { navController.popBackStack() }
            )
        }

    }
}
