package com.example.animalcrossingapp.roomtest1

import androidx.room.Dao
import androidx.room.Query
import com.example.animalcrossingapp.roomtest.Animal

@Dao
interface TestDao {
    @Query("SELECT * FROM testdb")
    fun selectAll(): List<TestVo>

    @Query("SELECT nm FROM testdb WHERE aid = 1 ")
    fun select(): Array<String>
}