package com.shivam.moviesnow.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shivam.moviesnow.model.MovieList

@Database(entities = [MovieList::class], version = 1)
@TypeConverters(TypeConvertersClass::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao?

    companion object {
        private const val DATABASE_NAME = "movies_database.db"

        @Volatile
        var instance: AppDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return instance
        }
    }
}