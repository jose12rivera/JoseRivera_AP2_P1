package edu.ucne.joserivera_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.joserivera_ap2_p1.presentation.navigation.AppNavigation
import edu.ucne.joserivera_ap2_p1.presentation.tareas.TareaviewModel
import edu.ucne.joserivera_ap2_p1.ui.theme.JoseRivera_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoseRivera_AP2_P1Theme() {
                 Surface(
                     modifier = Modifier.fillMaxSize(),
                     color = MaterialTheme.colorScheme.background
                 ) {
                     val navController = rememberNavController()
                     val viewModel: TareaviewModel = hiltViewModel()

                     AppNavigation(
                         navController = navController,
                         viewModel = viewModel
                     )
                 }
             }
        }
    }
}