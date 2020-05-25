package com.example.animalcrossingapp.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnimalDAO {
    @Query("SELECT * FROM animaldb")
    fun selectAll(): List<AnimalVO>

    @Query("SELECT * FROM animaldb WHERE name LIKE :name AND sort IN (:arrSort) AND price >= :min AND price <= :max AND loc IN (:loc)")
    fun select(name: String, arrSort: ArrayList<String>, min: Int, max: Int, loc: ArrayList<String>): List<AnimalVO>

    @Query("SELECT * FROM animaldb WHERE aid = :id")
    fun selectId(id: Int): AnimalVO

    @Query("SELECT * FROM animaldb WHERE sort IN ('B')")
    fun selectBug(): List<AnimalVO>

    @Query("SELECT * FROM animaldb WHERE sort IN ('F') AND flag = '1'")
    fun selectCatchFish(): List<AnimalVO>

    @Query("SELECT * FROM animaldb WHERE sort IN ('F') AND flag = '1'")
    fun selectCatchBug(): List<AnimalVO>

}