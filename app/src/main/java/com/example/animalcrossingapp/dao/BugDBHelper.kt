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
        val newRowId = db.insert(DBContract.BugEntry.TABLE_NAMEB, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_USER_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(userid: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_USER_ID + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var age: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                age = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_AGE))

                users.add(UserModel(userid, name, age))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllBugs(): ArrayList<BugVO> {
        val bugs = ArrayList<BugVO>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.BugEntry.TABLE_NAMEB, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name_japan: String
        var price: Int
        var catch_flag: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name_japan = cursor.getString(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_NAME_JAPAN))
                price = cursor.getInt(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_PRICE))
                catch_flag = cursor.getString(cursor.getColumnIndex(DBContract.BugEntry.COLUMN_CATCH_FLAG))


                bugs.add(BugVO(name_japan, price, catch_flag, ""))
                cursor.moveToNext()
            }
        }
        return bugs
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "BugReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.BugEntry.TABLE_NAMEB + " (" +
                    DBContract.BugEntry.COLUMN_NAME_JAPAN + " TEXT," +
                    DBContract.BugEntry.COLUMN_PRICE + " INTEGER," +
                    DBContract.BugEntry.COLUMN_CATCH_FLAG + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.BugEntry.TABLE_NAMEB
    }

}