package edu.ucne.joserivera_ap2_p1.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.joserivera_ap2_p1.data.local.dao.TareaDao
import edu.ucne.joserivera_ap2_p1.data.local.database.TareaDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): TareaDb =
        Room.databaseBuilder(
            context,
            TareaDb::class.java,
            "tareadb.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTareaDao(appDatabase: TareaDb): TareaDao =
        appDatabase.tareaDao()
}
