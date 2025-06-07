package edu.ucne.joserivera_ap2_p1.presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    object TareaList : Screen()

    @Serializable
    data class Tarea(val tareaId: Int) : Screen()
}
