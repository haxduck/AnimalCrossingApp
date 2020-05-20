package com.example.animalcrossingapp.vo

open class AllVO (
    val name_japan : String,
    val price : Int,
    val catch_flag : String,
    val sort: String
){
    override fun toString(): String {
        return "AllVO(name_japan='$name_japan', price=$price, catch_flag='$catch_flag', sort='$sort')"
    }
}