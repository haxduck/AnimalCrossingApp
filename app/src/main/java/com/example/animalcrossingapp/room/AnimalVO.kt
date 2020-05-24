package com.example.animalcrossingapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animaldb")
data class AnimalVO (
    @PrimaryKey(autoGenerate = true) var aid: Int = 0,
    @ColumnInfo(name = "name") var name: String? ="",
    @ColumnInfo(name = "pic") var pic: String? ="",
    @ColumnInfo(name = "shw") var shw: String? ="",
    @ColumnInfo(name = "shwPic") var shwPic: String? ="",
    @ColumnInfo(name = "price") var price: Int = 0,
    @ColumnInfo(name = "loc") var loc: String? ="",
    @ColumnInfo(name = "nm") var nm: String? ="",
    @ColumnInfo(name = "sm") var sm: String? ="",
    @ColumnInfo(name = "time") var time: String? ="",
    @ColumnInfo(name = "sort") var sort: String? ="",
    @ColumnInfo(name = "flag") var flag: String? = "",
    @ColumnInfo(name = "nmonths") var nmonths: String? = "",
    @ColumnInfo(name = "smonths") var smonths: String? = "",
    @ColumnInfo(name = "timeString") var timeString: String? = ""
)