package edu.ucne.joserivera_ap2_p1.presentation.tareas

import TareaEvent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import edu.ucne.joserivera_ap2_p1.data.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaviewModel @Inject constructor(
    private val repository: TareaRepository
) : ViewModel() {
    private val _tarealist = MutableStateFlow<List<TareaEntity>>(emptyList())
    val tarealist: StateFlow<List<TareaEntity>> = _tarealist.asStateFlow()

    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState: StateFlow<TareaUiState> = _uiState.asStateFlow()

    private val _tareasCache = mutableMapOf<Int, TareaEntity>()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _tarealist.value = list
            }
        }
    }

    fun loadTarea(id: Int) {
        viewModelScope.launch {
            val tarea = repository.find(id)
            tarea?.let {
                _tareasCache[id] = it
                _uiState.value = TareaUiState(
                    tareaId = it.tareaid,
                    descripcion = it.descripcion,
                    tiempo = it.tiempo.toString()
                )
            }
        }
    }

    fun getTareaById(id: Int): TareaEntity? {
        return _tareasCache[id] ?: run {
            viewModelScope.launch {
                repository.find(id)?.let { _tareasCache[id] = it }
            }
            _tareasCache[id]
        }
    }

    fun deleteTarea(tarea: TareaEntity) {
        viewModelScope.launch {
            repository.delete(tarea)
            _tareasCache.remove(tarea.tareaid)
        }
    }


    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.DescripcionChange -> {
                _uiState.value = _uiState.value.copy(
                    descripcion = event.descripcion,
                    errorMessage = null
                )
            }
            is TareaEvent.TiempoChange -> {
                _uiState.value = _uiState.value.copy(
                    tiempo = event.tiempo,
                    errorMessage = null
                )
            }
            is TareaEvent.Save -> {
                saveTarea()
            }
        }
    }

    private fun saveTarea() {
        val current = _uiState.value
        val tiempoInt = current.tiempo.toIntOrNull()

        if (current.descripcion.isBlank()) {
            _uiState.value = current.copy(errorMessage = "La descripción no puede estar vacía")
            return
        }

        if (tiempoInt == null || tiempoInt <= 0) {
            _uiState.value = current.copy(errorMessage = "El tiempo debe ser un número mayor a 0")
            return
        }

        val tarea = TareaEntity(
            tareaid = current.tareaId ?: 0,
            descripcion = current.descripcion,
            tiempo = tiempoInt
        )

        viewModelScope.launch {
            repository.save(tarea)
            _uiState.value = TareaUiState() // reset
        }
    }
}