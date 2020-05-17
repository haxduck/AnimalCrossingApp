package com.example.animalcrossingapp.vo

import java.io.Serializable

class BugVO(name_japan: String, price : Int, catch_flag: String, sort: String) : AllVO(name_japan, price, catch_flag, sort), Serializable {
    override fun toString(): String {
        return "BugVO(name_japan='$name_japan', price=$price, catch_flag='$catch_flag', sort='$sort')"
    }
}