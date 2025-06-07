package edu.ucne.joserivera_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.joserivera_ap2_p1.data.local.dao.TareaDao
import edu.ucne.joserivera_ap2_p1.data.local.entities.TareaEntity

@Database(
    entities = [TareaEntity::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}
