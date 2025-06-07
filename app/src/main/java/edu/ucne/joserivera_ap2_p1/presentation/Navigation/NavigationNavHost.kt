package edu.ucne.joserivera_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.joserivera_ap2_p1.presentation.HomeScreen
import edu.ucne.joserivera_ap2_p1.presentation.tareas.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "tareas"
    ) {
        composable("tareas") {
            TareaListScreen(
                goToTarea = { tareaId -> navController.navigate("tarea/$tareaId") },
                createTarea = { navController.navigate("tarea/0") }
            )
        }

        composable(
            "tarea/{tareaId}",
            arguments = listOf(navArgument("tareaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getInt("tareaId") ?: 0
            TareaScreen(
                tareaId = tareaId,
                goBack = { navController.popBackStack() }
            )
        }
    }
}
