package com.example.animalcrossingapp.database

import androidx.room.Database
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
}