package com.example.animalcrossingapp.roomtest

import androidx.room.*
import java.time.Month

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    fun selectAll(): List<Animal>

    @Query("SELECT * FROM animals WHERE aid IN (:animalIds)")
    fun loadAllByIds(animalIds: IntArray): List<Animal>

    @Query("SELECT * FROM animals WHERE name LIKE :name AND price >= :min AND price <= :max AND sort LIKE :sort AND month IN (:month) AND habitant IN (:habitant)")
    fun select(
        name: String,
        min: Int,
        max: Int,
        sort: String,
        month: ArrayList<String>,
        habitant: ArrayList<String>
    ): List<Animal>


    @Insert
    fun insertAll(vararg animals: Animal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(animal: Animal)

    @Delete
    fun delete(animal: Animal)

    @Update
    fun update(animal: Animal)
}