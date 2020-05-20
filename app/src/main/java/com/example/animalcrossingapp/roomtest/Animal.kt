package com.example.animalcrossingapp.roomtest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class Animal (
    @PrimaryKey(autoGenerate = true) var aid: Int = 0,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "price") var price: Int? = 0,
    @ColumnInfo(name = "catch_flag") var catch_flag: String? = "",
    @ColumnInfo(name = "sort") var sort: String? = "",
    @ColumnInfo(name = "month") var month: String? = "",
    @ColumnInfo(name = "habitant") var habitant: String? = ""
)