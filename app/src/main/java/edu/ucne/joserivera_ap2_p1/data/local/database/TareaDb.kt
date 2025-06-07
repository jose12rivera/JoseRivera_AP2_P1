package edu.ucne.joserivera_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.joserivera_ap2_p1.data.local.dao.Tareadao
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

@Database(
    entities = [
        TareaEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class TareaDb :RoomDatabase(){
    abstract fun Tareadao(): Tareadao
}