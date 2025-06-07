package edu.ucne.joserivera_ap2_p1.presentation.tareas

data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String = "",
    val tiempo: String = "",
    val errorMessage: String? = null
)
