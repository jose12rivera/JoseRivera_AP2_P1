package edu.ucne.joserivera_ap2_p1.presentation.tareas

sealed interface TareaEvent {
    data class DescripcionChange(val descripcion: String) : TareaEvent
    data class TiempoChange(val tiempo: Int) : TareaEvent
    data class LoadTarea(val tareaId: Int) : TareaEvent
    object Save : TareaEvent
    object Delete : TareaEvent
    object New : TareaEvent
    data class DeleteById(val tareaId: Int) : TareaEvent

}