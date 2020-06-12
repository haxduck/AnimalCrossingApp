package com.example.animalcrossingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Information::class,
                                Capture::class,
                                Habitat::class,
                                Month::class,
                                Month_Information::class,
                                Time::class,
                                Time_Infomation::class
                                ), version = 1)
abstract class AnimalCrossingDB : RoomDatabase() {
    abstract fun animalCrossingDao(): AnimalCrossingDAO
    companion object {
        private var INSTANCE: AnimalCrossingDB? = null

        fun getInstance(context: Context): AnimalCrossingDB? {
            if (INSTANCE == null) {
                synchronized(AnimalCrossingDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AnimalCrossingDB::class.java, "AnimalCrossing.db"
                    ).allowMainThreadQueries().createFromAsset("AnimalCrossing.db").build()
                    // ).allowMainThreadQueries().createFromAsset("AnimalCrossing.db").build()
                }
            }
            return INSTANCE
        }
    }
}