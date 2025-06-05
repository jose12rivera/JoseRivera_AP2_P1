package edu.ucne.joserivera_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Tareadao {
    @Upsert()
    suspend fun save(tarea:TareaEntity)
    @Query("""Select*From tareas 
        Where tareaid=:id 
Limit 1""")

    suspend fun find(id: Int): TareaEntity?
    @Delete
    suspend fun delete(tareaEntity: TareaEntity)
    @Query("Select *From tareas")
    fun getAll(): Flow<List<TareaEntity>>
}
