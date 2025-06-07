package edu.ucne.joserivera_ap2_p1.presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object tarealist: Screen()

    @Serializable
    data class tarea (val tareaid: Int): Screen()

    @Serializable
    data class tareadelete (val tareaid: Int): Screen()

    @Serializable
    data class tareaeditar (val tareaid: Int): Screen()
}