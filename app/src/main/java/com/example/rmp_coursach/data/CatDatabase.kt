package com.example.rmp_coursach.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rmp_coursach.model.Cat

@Database(entities = [Cat::class], version = 1)
abstract class CatDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

    companion object {
        @Volatile
        private var INSTANCE: CatDatabase? = null

        @JvmStatic // чтобы вызывать из Java
        fun getDatabase(context: Context): CatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java,
                    "cat_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
