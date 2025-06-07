// data/local/entities/TareaEntity.kt
package edu.ucne.joserivera_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tareas")
data class TareaEntity (
    @PrimaryKey(autoGenerate = true)
    val tareaId: Int = 0,
    val descripcion: String = "",
    val tiempo: Int = 0
)
