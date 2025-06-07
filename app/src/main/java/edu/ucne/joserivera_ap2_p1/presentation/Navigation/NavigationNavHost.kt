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
            TareaBodyScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent,
                goBack = { navController.popBackStack() }
            )
        }

        composable("editar_tarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
            LaunchedEffect(tareaId) {
                viewModel.loadTarea(tareaId)
            }

            TareaBodyScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent,
                goBack = { navController.popBackStack() }
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
                // Mostrar mensaje de error o redirigir
                navController.popBackStack()
            }
        }
    }
}