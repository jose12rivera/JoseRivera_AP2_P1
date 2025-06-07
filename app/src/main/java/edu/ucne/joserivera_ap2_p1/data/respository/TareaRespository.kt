package edu.ucne.joserivera_ap2_p1.data.respository

import edu.ucne.joserivera_ap2_p1.data.local.dao.Tareadao
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

class TareaRespository (
    private val tareadao: Tareadao
){
    suspend fun save(tarea:TareaEntity)=tareadao.save(tarea)

    suspend fun find(id: Int): TareaEntity?=tareadao.find(id)
    suspend fun delete(tarea: TareaEntity) =tareadao.delete(tarea)

    fun getAll(): Flow<List<TareaEntity>> =tareadao.getAll()
}