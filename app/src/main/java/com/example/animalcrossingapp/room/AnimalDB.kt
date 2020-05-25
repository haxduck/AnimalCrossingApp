package com.example.animalcrossingapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(AnimalVO::class), version = 3)
abstract class AnimalDB : RoomDatabase() {
    abstract fun animalDao(): AnimalDAO

    companion object {
        private var INSTANCE: AnimalDB? = null

        fun getInstance(context: Context): AnimalDB? {
            if (INSTANCE == null) {
                synchronized(AnimalDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AnimalDB::class.java, "animaldb.db"
                    ).allowMainThreadQueries().createFromAsset("animaldb.db").build()
                }
            }
            return INSTANCE
        }
    }
}