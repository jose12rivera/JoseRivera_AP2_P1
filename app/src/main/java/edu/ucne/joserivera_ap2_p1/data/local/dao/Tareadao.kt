package edu.ucne.joserivera_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tarea: TareaEntity)

    @Query("SELECT * FROM Tareas WHERE tareaid = :id LIMIT 1")
    suspend fun find(id: Int): TareaEntity?

    @Delete
    suspend fun delete(tareaEntity: TareaEntity)

    @Query("SELECT * FROM Tareas")
    fun getAll(): Flow<List<TareaEntity>>
}
