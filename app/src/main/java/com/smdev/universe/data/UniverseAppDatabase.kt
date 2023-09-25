package com.smdev.universe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UniverseAppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        fun getDatabase(context: Context) = Room.databaseBuilder(
                context,
                UniverseAppDatabase::class.java, "database-name"
            ).build()
    }
}