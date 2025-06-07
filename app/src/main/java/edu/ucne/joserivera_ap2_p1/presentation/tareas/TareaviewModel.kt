package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import edu.ucne.joserivera_ap2_p1.data.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareasViewModel @Inject constructor(
    private val tareasRepository: TareaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTareas()
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.DescripcionChange -> updateDescripcion(event.descripcion)
            is TareaEvent.TiempoChange -> updateTiempo(event.tiempo)
            TareaEvent.Save -> saveTarea()
            TareaEvent.Delete -> deleteTarea()
            TareaEvent.New -> newTarea()
            is TareaEvent.LoadTarea -> loadTarea(event.tareaId)
            is TareaEvent.DeleteById -> deleteTareaById(event.tareaId)
        }
    }

    private fun loadTareas() {
        viewModelScope.launch {
            tareasRepository.getAll().collect { tareas ->
                _uiState.update { it.copy(tareas = tareas) }
            }
        }
    }

    private fun loadTarea(tareaId: Int) {
        viewModelScope.launch {
            if (tareaId > 0) {
                tareasRepository.find(tareaId)?.let { tarea ->
                    _uiState.update {
                        it.copy(
                            tareaId = tarea.tareaId,
                            descripcion = tarea.descripcion,
                            tiempo = tarea.tiempo,
                            errorMessage = null
                        )
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        tareaId = 0,
                        descripcion = "",
                        tiempo = 0,
                        errorMessage = null
                    )
                }
            }
        }
    }
    private fun deleteTareaById(tareaId: Int) {
        viewModelScope.launch {
            tareasRepository.find(tareaId)?.let {
                tareasRepository.delete(it)
            }
        }
    }


    private fun saveTarea() {
        viewModelScope.launch {
            val current = _uiState.value
            if (validateFields(current)) {
                val tarea = current.toEntity()
                tareasRepository.save(tarea)
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun deleteTarea() {
        viewModelScope.launch {
            _uiState.value.tareaId?.let { id ->
                if (id > 0) {
                    tareasRepository.delete(_uiState.value.toEntity())
                }
            }
        }
    }

    private fun newTarea() {
        _uiState.update {
            it.copy(
                tareaId = 0,
                descripcion = "",
                tiempo = 0,
                errorMessage = null
            )
        }
    }

    private fun updateDescripcion(descripcion: String) {
        _uiState.update { it.copy(descripcion = descripcion) }
    }

    private fun updateTiempo(tiempo: Int) {
        _uiState.update { it.copy(tiempo = tiempo) }
    }

    private fun validateFields(state: TareaUiState): Boolean {
        return when {
            state.descripcion.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "La descripción no puede estar vacía") }
                false
            }
            state.tiempo <= 0 -> {
                _uiState.update { it.copy(errorMessage = "El tiempo debe ser mayor a 0") }
                false
            }
            else -> true
        }
    }
}

// Extensión para convertir el estado UI en entidad
private fun TareaUiState.toEntity(): TareaEntity {
    return TareaEntity(
        tareaId = this.tareaId ?: 0,
        descripcion = this.descripcion,
        tiempo = this.tiempo
    )
}
