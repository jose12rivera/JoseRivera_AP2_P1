package edu.ucne.joserivera_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tareas")
data class TareaEntity(
    @PrimaryKey

    val tareaid: Int?,
    val descripcion: String="",
    val tiempo:  Double

)