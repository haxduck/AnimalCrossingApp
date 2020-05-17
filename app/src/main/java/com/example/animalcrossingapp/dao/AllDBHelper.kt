package com.example.animalcrossingapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.animalcrossingapp.vo.AllVO

import java.util.ArrayList

class AllDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertAll(all: AllVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.AllEntry.COLUMN_NAME_JAPAN, all.name_japan)
        values.put(DBContract.AllEntry.COLUMN_PRICE, all.price)
        values.put(DBContract.AllEntry.COLUMN_CATCH_FLAG, all.catch_flag)
        values.put(DBContract.AllEntry.COLUMN_SORT, all.sort)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.AllEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateAll(all: AllVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.AllEntry.COLUMN_NAME_JAPAN, all.name_japan)
        values.put(DBContract.AllEntry.COLUMN_PRICE, all.price)
        values.put(DBContract.AllEntry.COLUMN_CATCH_FLAG, all.catch_flag)
        // Define 'where' part of query.
        val selection = DBContract.AllEntry.COLUMN_NAME_JAPAN + " LIKE ?"
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
        val selection = DBContract.AllEntry.COLUMN_NAME_JAPAN + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(all.name_japan)
        // Issue SQL statement.
        db.delete(DBContract.AllEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readAll(all: String): ArrayList<AllVO> {
        val alls = ArrayList<AllVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.AllEntry.TABLE_NAME + " WHERE " + DBContract.AllEntry.COLUMN_NAME_JAPAN + "='" + all + "'", null)
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
                price = cursor.getInt(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_CATCH_FLAG))
                sort = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_SORT))

                alls.add(AllVO(all, price, catch_flag, sort))
                cursor.moveToNext()
            }
        }
        return alls
    }

    fun readAll(): ArrayList<AllVO> {
        val alles = ArrayList<AllVO>()
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
                name_japan = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_NAME_JAPAN))
                price = cursor.getInt(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_CATCH_FLAG))
                sort = cursor.getString(cursor.getColumnIndex(DBContract.AllEntry.COLUMN_SORT))

                alles.add(AllVO(name_japan, price, catch_flag, sort))
                cursor.moveToNext()
            }
        }
        return alles
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "AllReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.AllEntry.TABLE_NAME + " (" +
                    DBContract.AllEntry.COLUMN_NAME_JAPAN + " TEXT," +
                    DBContract.AllEntry.COLUMN_PRICE + " INTEGER," +
                    DBContract.AllEntry.COLUMN_CATCH_FLAG + " TEXT," +
                    DBContract.AllEntry.COLUMN_SORT + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.AllEntry.TABLE_NAME
    }

}