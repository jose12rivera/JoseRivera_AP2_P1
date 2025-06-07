package edu.ucne.joserivera_ap2_p1.presentation.tareas

import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String = "",
    val tiempo: Int = 0,
    val errorMessage: String? = null,
    val tareas: List<TareaEntity> = emptyList(),
    val isSaveSuccessful: Boolean = false
)