// AppNavigation.kt
package edu.ucne.joserivera_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.joserivera_ap2_p1.presentation.HomeScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: TareaviewModel
) {
    val tarealist by viewModel.tarealist.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController = navController) }

        composable("tarea_list") {
            TareaListScreen(
                tarealist = tarealist,
                onCreate = { navController.navigate("tarea_nueva") },
                onDelete = { tareaId -> navController.navigate("eliminar_tarea/$tareaId") },
                onEditar = { tareaId -> navController.navigate("editar_tarea/$tareaId") }
            )
        }

        composable("tarea_nueva") {
            // Resetear el estado para nueva tarea
            LaunchedEffect(Unit) {
                viewModel.loadTarea(0)
            }
            TareaEditarScreen(
                tareaId = 0,
                viewModel = viewModel,
                onGuardar = { navController.popBackStack() },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable("editar_tarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0

            // Cargar la tarea cuando entra a editar
            LaunchedEffect(tareaId) {
                if (tareaId > 0) {
                    viewModel.loadTarea(tareaId)
                }
            }

            TareaEditarScreen(
                tareaId = tareaId,
                viewModel = viewModel,
                onGuardar = { navController.popBackStack() },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable("eliminar_tarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
            val tarea = viewModel.getTareaById(tareaId)

            if (tarea != null) {
                DeleteTareaScreen(
                    tarea = tarea,
                    onDelete = {
                        viewModel.deleteTarea(it)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}