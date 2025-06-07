package edu.ucne.joserivera_ap2_p1.presentation.tareas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import edu.ucne.joserivera_ap2_p1.data.repository.TareaRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaviewModel @Inject constructor(
    private val tareaRespository: TareaRepository
) : ViewModel() {

    private val _tarealist = MutableStateFlow<List<TareaEntity>>(emptyList())
    val tarealist: StateFlow<List<TareaEntity>> = _tarealist.asStateFlow()

    init {
        loadTareas()
    }

    fun loadTareas() {
        viewModelScope.launch {
            tareaRespository.getAll().collect { lista ->
                Log.d("TareaviewModel", "Lista recibida: ${lista.size}")
                _tarealist.value = lista
            }
        }
    }

    fun updateTarea(tarea: TareaEntity) {
        saveTarea(tarea)
    }

    fun agregarTarea(descripcion: String, tiempo: Int, id: Int? = null) {
        val tarea = TareaEntity(
            tareaid = id ?: 0,
            descripcion = descripcion,
            tiempo = tiempo
        )
        saveTarea(tarea)
    }

    private fun saveTarea(tarea: TareaEntity) {
        viewModelScope.launch {
            tareaRespository.save(tarea)
            loadTareas()
        }
    }

    fun deleteTarea(tarea: TareaEntity) {
        viewModelScope.launch {
            tareaRespository.delete(tarea)
            loadTareas()
        }
    }

    fun getTareaById(id: Int): TareaEntity? {
        return _tarealist.value.find { it.tareaid == id }
    }
}