package com.example.animalcrossingapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "first_name") var firstName: String? = "",
    @ColumnInfo(name = "last_name") var lastName: String? = ""
)
