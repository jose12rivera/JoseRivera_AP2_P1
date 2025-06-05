package edu.ucne.joserivera_ap2_p1.presentation.tareas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import edu.ucne.joserivera_ap2_p1.data.respository.TareaRespository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TareaviewModel (
    private val tareaRespository: TareaRespository
): ViewModel(){
    private val _tarealist= MutableStateFlow<List<TareaEntity>>(emptyList())
    val tarealist:StateFlow<List<TareaEntity>> get() = _tarealist

    init {
        LoadTareas()
    }

    fun LoadTareas(){
        viewModelScope.launch {
            tareaRespository.getAll().collect { Lista->
                Log.d("TareaviewModel","Lista recibida:${Lista.size}")
                _tarealist.value=Lista
            }
        }
    }
    fun Update(tarea: TareaEntity){
        savetarea(tarea)
    }

    fun Agregartarea(descripcion: String, tiempo: Double){
        val tarea= TareaEntity(
            tareaid = null,
            descripcion=descripcion,
            tiempo=tiempo
        )
        savetarea(tarea)
    }
    fun savetarea(tarea: TareaEntity){
        viewModelScope.launch {
            tareaRespository.save(tarea)
            LoadTareas()
        }
    }
    fun tareadelete(tarea: TareaEntity){
        viewModelScope.launch {
            tareaRespository.delete(tarea)
            LoadTareas()
        }
    }

    fun gettareaIdBY(id: Int): TareaEntity? {
        return  _tarealist.value.find { it.tareaid==id }
    }

}