package com.example.animalcrossingapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.animalcrossingapp.vo.BugVO

import java.util.ArrayList

class BugDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertBug(bug: BugVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.BugEntry.COLUMN_NAME_JAPAN, bug.name_japan)
        values.put(DBContract.BugEntry.COLUMN_PRICE, bug.price)
        values.put(DBContract.BugEntry.COLUMN_CATCH_FLAG, bug.catch_flag)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.BugEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateBug(bug: BugVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.BugEntry.COLUMN_NAME_JAPAN, bug.name_japan)
        values.put(DBContract.BugEntry.COLUMN_PRICE, bug.price)
        values.put(DBContract.BugEntry.COLUMN_CATCH_FLAG, bug.catch_flag)
        // Define 'where' part of query.
        val selection = DBContract.BugEntry.COLUMN_NAME_JAPAN + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(bug.name_japan)
        // Issue SQL statement.
        db.update(DBContract.BugEntry.TABLE_NAME,values ,selection, selectionArgs)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteBug(bug: BugVO): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.BugEntry.COLUMN_NAME_JAPAN + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(bug.name_japan)
        // Issue SQL statement.
        db.delete(DBContract.BugEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readBug(bug: BugVO): ArrayList<BugVO> {
        val buges = ArrayList<BugVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.BugEntry.TABLE_NAME + " WHERE " + DBContract.BugEntry.COLUMN_NAME_JAPAN + "='" + bug.name_japan + "'", null)
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
                price = cursor.getInt(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_CATCH_FLAG))

                buges.add(BugVO(bug.name_japan, price, catch_flag, sort= "b"))
                cursor.moveToNext()
            }
        }
        return buges
    }

    fun readAllBuges(): ArrayList<BugVO> {
        val buges = ArrayList<BugVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.BugEntry.TABLE_NAME, null)
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
                name_japan = cursor.getString(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_NAME_JAPAN))
                price = cursor.getInt(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_CATCH_FLAG))

                buges.add(BugVO(name_japan, price, catch_flag, sort))
                cursor.moveToNext()
            }
        }
        return buges
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "BugReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.BugEntry.TABLE_NAME + " (" +
                    DBContract.BugEntry.COLUMN_NAME_JAPAN + " TEXT," +
                    DBContract.BugEntry.COLUMN_PRICE + " INTEGER," +
                    DBContract.BugEntry.COLUMN_CATCH_FLAG + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.BugEntry.TABLE_NAME
    }

}