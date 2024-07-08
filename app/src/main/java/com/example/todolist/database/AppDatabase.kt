package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.entities.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao

    companion object {
        private lateinit var instance: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                            .allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}