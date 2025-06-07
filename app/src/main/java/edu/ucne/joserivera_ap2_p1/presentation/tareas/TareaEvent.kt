sealed class TareaEvent {
    data class DescripcionChange(val descripcion: String) : TareaEvent()
    data class TiempoChange(val tiempo: String) : TareaEvent()
    object Save : TareaEvent()
}
