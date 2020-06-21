package com.sunny.learn.pcfassignmentapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class PCFDataBase(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "PlatformCommonsFoundation"
        private val TABLE = "RockStars"

        private val ID = "id"
        private val NAME = "name"
        private val FULL_NAME = "full_name"
        private val DESCRIPTION = "description"
        private val AVATAR_URL = "avatar_url"
        private val HTML_URL = "html_url"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE (" +
                "$ID TEXT, " +
                "$NAME TEXT, " +
                "$FULL_NAME TEXT, " +
                "$DESCRIPTION TEXT, " +
                "$AVATAR_URL TEXT, " +
                "$HTML_URL TEXT )"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun addRockStar(result: Result): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, result.id)
        contentValues.put(NAME, result.name) // EmpModelClass Name
        contentValues.put(FULL_NAME, result.full_name) // EmpModelClass Name
        contentValues.put(DESCRIPTION, result.description) // EmpModelClass Name
        contentValues.put(AVATAR_URL, result.owner.avatar_url) // EmpModelClass Name
        contentValues.put(HTML_URL, result.owner.html_url) // EmpModelClass Name

        // Inserting Row
        val success = db.insert(TABLE, null, contentValues)

        db.close()

        return success
    }

    fun getAllRockStars(): List<Result> {
        val rsList: ArrayList<Result> = ArrayList<Result>()
        val selectQuery = "SELECT  * FROM $TABLE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var full_name: String
        var description: String
        var avatar_url: String
        var html_url: String


        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                full_name = cursor.getString(cursor.getColumnIndex("full_name"))
                description =
                    if (cursor.getString(cursor.getColumnIndex("description")) != null) {
                        cursor.getString(cursor.getColumnIndex("description"))
                    } else {
                        "NA"
                    }
                avatar_url = cursor.getString(cursor.getColumnIndex("avatar_url"))
                html_url = cursor.getString(cursor.getColumnIndex("html_url"))
                val result = Result(id, name, full_name, description, Owner(avatar_url, html_url))

                rsList.add(result)
            } while (cursor.moveToNext())
        }
        return rsList
    }
}