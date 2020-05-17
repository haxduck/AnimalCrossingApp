package com.example.animalcrossingapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.animalcrossingapp.vo.FishVO

import java.util.ArrayList

class FishDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertFish(fish: FishVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.FishEntry.COLUMN_NAME_JAPAN, fish.name_japan)
        values.put(DBContract.FishEntry.COLUMN_PRICE, fish.price)
        values.put(DBContract.FishEntry.COLUMN_CATCH_FLAG, fish.catch_flag)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.FishEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateFish(fish: FishVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.FishEntry.COLUMN_NAME_JAPAN, fish.name_japan)
        values.put(DBContract.FishEntry.COLUMN_PRICE, fish.price)
        values.put(DBContract.FishEntry.COLUMN_CATCH_FLAG, fish.catch_flag)
        // Define 'where' part of query.
        val selection = DBContract.FishEntry.COLUMN_NAME_JAPAN + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(fish.name_japan)
        // Issue SQL statement.
        db.update(DBContract.FishEntry.TABLE_NAME,values ,selection, selectionArgs)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteFish(fish: FishVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.FishEntry.COLUMN_NAME_JAPAN + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(fish.name_japan)
        // Issue SQL statement.
        db.delete(DBContract.FishEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readFish(fish: FishVO): ArrayList<FishVO> {
        val fishes = ArrayList<FishVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.FishEntry.TABLE_NAME + " WHERE " + DBContract.FishEntry.COLUMN_NAME_JAPAN + "='" + fish.name_japan + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var price: Int
        var catch_flag: String
        var sort: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                price = cursor.getInt(cursor.getColumnIndex(DBContract.FishEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.FishEntry.COLUMN_CATCH_FLAG))
                sort = "f"

                fishes.add(FishVO(fish.name_japan, price, catch_flag, sort))
                cursor.moveToNext()
            }
        }
        return fishes
    }

    fun readAllFishes(): ArrayList<FishVO> {
        val fishes = ArrayList<FishVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.FishEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name_japan: String
        var price: Int
        var catch_flag: String
        var sort: String = ""
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name_japan = cursor.getString(cursor.getColumnIndex(DBContract.FishEntry.COLUMN_NAME_JAPAN))
                price = cursor.getInt(cursor.getColumnIndex(DBContract.FishEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.FishEntry.COLUMN_CATCH_FLAG))

                fishes.add(FishVO(name_japan, price, catch_flag, sort))
                cursor.moveToNext()
            }
        }
        return fishes
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FishReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.FishEntry.TABLE_NAME + " (" +
                    DBContract.FishEntry.COLUMN_NAME_JAPAN + " TEXT," +
                    DBContract.FishEntry.COLUMN_PRICE + " INTEGER," +
                    DBContract.FishEntry.COLUMN_CATCH_FLAG + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.FishEntry.TABLE_NAME
    }

}