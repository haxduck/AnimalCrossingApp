package com.example.animalcrossingapp.roomtest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Animal::class), version = 1)
abstract class AnimalDB : RoomDatabase() {
    abstract fun anmalDao(): AnimalDao
}