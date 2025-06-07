package edu.ucne.joserivera_ap2_p1.data.repository

import edu.ucne.joserivera_ap2_p1.data.local.dao.TareaDao
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TareaRepository @Inject constructor(
    private val tareaDao: TareaDao
) {
    suspend fun save(tarea: TareaEntity) = tareaDao.save(tarea)

    suspend fun find(id: Int): TareaEntity? = tareaDao.find(id)

    suspend fun delete(tarea: TareaEntity) = tareaDao.delete(tarea)

    fun getAll(): Flow<List<TareaEntity>> = tareaDao.getAll()
}
