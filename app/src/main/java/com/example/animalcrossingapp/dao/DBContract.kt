package com.example.animalcrossingapp.dao

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_USER_ID = "userid"
            val COLUMN_NAME = "name"
            val COLUMN_AGE = "age"
        }
    }

    class FishEntry : BaseColumns {
        companion object {
            val TABLE_NAMEF = "Fishes"
            val COLUMN_NAME_JAPAN = "name_japan"
            val COLUMN_PRICE = "price"
            val COLUMN_CATCH_FLAG = "catch_flag"
        }
    }
}