package com.example.animalcrossingapp.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

class Tables{
}

@Parcelize
@Entity(tableName = "Information")
data class Information (
    @PrimaryKey
    @NotNull
    @ColumnInfo() val information_code: String = "",
    @NotNull val habitat_code: String = "",
    @NotNull var capture_code: String = "",
    val name_japan: String? = "",
    val name_korea: String? = "",
    @NotNull @ColumnInfo(defaultValue = "0") val price: Int = 0,
    @NotNull @ColumnInfo(defaultValue = "0") val catch_flag: String = "",
    val size: String? = "",
    val sortation: String? = ""
) : Parcelable

@Parcelize
@Entity(tableName = "Capture")
data class Capture (
    @PrimaryKey
    val capture_code: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val picture: ByteArray?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val shilhouette: ByteArray?
) : Parcelable

@Parcelize
@Entity(tableName = "Habitat")
data class Habitat (
    @PrimaryKey
    val habitat_code: String,
    val name_japan: String?,
    val name_korea: String?
) : Parcelable

@Parcelize
@Entity(tableName = "Month")
data class Month (
    @PrimaryKey
    val month_code: String,
    val month: String?,
    val location: String?
) : Parcelable

@Parcelize
@Entity(tableName = "Month_Information")
data class Month_Information (
    @PrimaryKey
    val month_information_seq: Int = 0,
    val information_code: String,
    val month_code: String
) : Parcelable

@Parcelize
@Entity(tableName = "Time")
data class Time (
    @PrimaryKey
    val time_code: String,
    val time: String?
) : Parcelable

@Parcelize
@Entity(tableName = "Time_Information")
data class Time_Infomation (
    @PrimaryKey
    val time_information_seq: Int,
    val information_code: String?,
    val time_code: String?
) : Parcelable

data class JoinTest(
    @Embedded val infomation: Information,
    @Relation(
        parentColumn = "habitat_code",
        entityColumn = "habitat_code"
    )
    val habitat: Habitat
)

@Parcelize
data class MyObject(
    val information_code: String,
    val name_japan: String?,
    val picture: ByteArray?,
    val price: Int,
    val catch_flag: String,
    val size: String?,
    val sortation: String?,
    val months: String?,
    val times: String?
) : Parcelable

@Parcelize
data class Current(
    val information_code: String?,
    val name: String?,
    val namek: String?,
    val price: Int,
//    val picture: ByteArray?,
    val time: String?,
    val month: String?,
    val flag: String,
    val habitat: String?, val habitatk: String?,
    val sortation: String?
) : Parcelable
