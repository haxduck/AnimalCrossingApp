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
            val TABLE_NAME = "Fishes"
            val COLUMN_NAME_JAPAN = "name_japan"
            val COLUMN_PRICE = "price"
            val COLUMN_CATCH_FLAG = "catch_flag"
        }
    }

    class BugEntry : BaseColumns {
        companion object {
            val TABLE_NAMEB = "Bugs"
            val COLUMN_NAME_JAPAN = "name_japan"
            val COLUMN_PRICE = "price"
            val COLUMN_CATCH_FLAG = "catch_flag"
        }
    }

    class AllEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "Test"
            val COLUMN_NAME = "name"
            val COLUMN_PRICE = "price"
            val COLUMN_CATCH_FLAG = "catch_flag"
            val COLUMN_SORT = "sort"
        }
    }
}