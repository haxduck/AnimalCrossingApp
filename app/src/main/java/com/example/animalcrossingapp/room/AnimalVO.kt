package com.example.animalcrossingapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "animaldb")
data class AnimalVO (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "aid") val aid: Int = 0,
    @ColumnInfo(name = "name") val name: String? ="",
    @ColumnInfo(name = "pic") val pic: String? ="",
    @ColumnInfo(name = "shw") val shw: String? ="",
    @ColumnInfo(name = "shwPic") val shwPic: String? ="",
    @ColumnInfo(name = "price") val price: Int = 0,
    @ColumnInfo(name = "loc") val loc: String? ="",
    @ColumnInfo(name = "nm") val nm: String? ="",
    @ColumnInfo(name = "sm") val sm: String? ="",
    @ColumnInfo(name = "time") val time: String? ="",
    @ColumnInfo(name = "sort") val sort: String? ="",
    @ColumnInfo(name = "flag") val flag: String? = "",
    @ColumnInfo(name = "nmonths") val nmonths: String? = "",
    @ColumnInfo(name = "smonths") val smonths: String? = "",
    @ColumnInfo(name = "timeString") val timeString: String? = ""
) : Parcelable