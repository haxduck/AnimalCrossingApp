package com.example.animalcrossingapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.animalcrossingapp.vo.AllVO

import java.util.ArrayList

class TestDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertTest(): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()


        values.put(Tbl.COLUMN_NAME, "fish2")
        values.put(Tbl.COLUMN_PRICE, 3000)
        values.put(Tbl.COLUMN_CATCH_FLAG, "0")
        values.put(Tbl.COLUMN_SORT, "f")
        values.put(Tbl.COLUMN_MONTH, "3月")
        values.put(Tbl.COLUMN_HABITANT, "住人")


        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(Tbl.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateAll(all: AllVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.AllEntry.COLUMN_NAME, all.name_japan)
        values.put(DBContract.AllEntry.COLUMN_PRICE, all.price)
        values.put(DBContract.AllEntry.COLUMN_CATCH_FLAG, all.catch_flag)
        // Define 'where' part of query.
        val selection = DBContract.AllEntry.COLUMN_NAME + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(all.name_japan)
        // Issue SQL statement.
        db.update(DBContract.AllEntry.TABLE_NAME,values ,selection, selectionArgs)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteAll(all: AllVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.AllEntry.COLUMN_NAME + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(all.name_japan)
        // Issue SQL statement.
        db.delete(DBContract.AllEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readTest(name: String, price: ArrayList<Int>, sort: String, month: ArrayList<String>, habitant: ArrayList<String>): ArrayList<Any> {
        val list = ArrayList<Any>()
        val db = writableDatabase
        var cursor: Cursor? = null

        var where: String = ""

        var stringName: String = "name = '" + name + "' "
        if (name == "") stringName = "name like '%'"

        var stringPrice: String = " AND price >= " + price.get(0) + " AND price <= " + price.get(1)
        if (price.get(0) == 0 and price.get(1)) stringPrice = ""

        var stringSort: String = " AND sort = '" + sort + "'"
        if (sort == "") stringSort = ""

        var stringMonth: String = " AND month in ("
        if (month.size > 0 ) {
            month.forEach {
                if(it != month.last()) stringMonth += "'" + it + "',"
                else stringMonth += "'" + it + "')"
            }
        } else {
            stringMonth = ""
        }

        var stringHabitant: String = " AND habitant in ("
        if (habitant.size > 0) {
            habitant.forEach {
                if(it != habitant.last()) stringHabitant += "'" + it + "',"
                else stringHabitant += "'" + it + "')"
            }
        } else {
            stringHabitant = ""
        }

        where = "WHERE " +
                stringName +
                stringPrice +
                stringSort +
                stringMonth +
                stringHabitant

        //try {
            cursor = db.rawQuery(
                "SELECT name, price, catch_flag, sort, month, habitant " +
                "FROM " + Tbl.TABLE_NAME + " " +
                where, null)
        /*} catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }*/
        Log.d("TestDBHelper", stringHabitant)

        var name: String
        var price: Int
        var catch_flag: String
        var sort: String
        var month: String
        var habitant: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(Tbl.COLUMN_NAME))
                price = cursor.getInt(cursor.getColumnIndex(Tbl.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(Tbl.COLUMN_CATCH_FLAG))
                sort = cursor.getString(cursor.getColumnIndex(Tbl.COLUMN_SORT))
                month = cursor.getString(cursor.getColumnIndex(Tbl.COLUMN_MONTH))
                habitant = cursor.getString(cursor.getColumnIndex(Tbl.COLUMN_HABITANT))


                list.add(name)
                list.add(price)
                list.add(catch_flag)
                list.add(sort)
                list.add(month)
                list.add(habitant)
                cursor.moveToNext()
            }
        }
        return list
    }

    fun readAll(): ArrayList<AllVO> {
        val alls = ArrayList<AllVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.AllEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name_japan: String
        var price: Int
        var catch_flag: String
        var sort: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name_japan = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_NAME))
                price = cursor.getInt(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_CATCH_FLAG))
                sort = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_SORT))

                alls.add(AllVO(name_japan, price, catch_flag, sort))
                cursor.moveToNext()
            }
        }
        return alls
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "TestReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Tbl.TABLE_NAME + " (" +
                    Tbl.COLUMN_NAME + " TEXT," +
                    Tbl.COLUMN_PRICE + " INTEGER," +
                    Tbl.COLUMN_CATCH_FLAG + " TEXT," +
                    Tbl.COLUMN_SORT + " TEXT," +
                    Tbl.COLUMN_MONTH + " TEXT," +
                    Tbl.COLUMN_HABITANT + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Tbl.TABLE_NAME
    }

}

class Tbl : BaseColumns {
    companion object {
        val TABLE_NAME = "Test"
        val COLUMN_NAME = "name"
        val COLUMN_PRICE = "price"
        val COLUMN_CATCH_FLAG = "catch_flag"
        val COLUMN_SORT = "sort"
        val COLUMN_MONTH = "month"
        val COLUMN_HABITANT = "habitant"
    }
}