package com.example.animalcrossingapp.roomtest1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TestVo::class) ,version = 3)
abstract class TestDB : RoomDatabase() {
    abstract fun testDao(): TestDao
}