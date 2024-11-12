package com.example.lab7.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClientDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "clientDatabase.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_CLIENTS = "clients"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRST_NAME = "firstName"
        private const val COLUMN_LAST_NAME = "lastName"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_SCHEDULE = "schedule"
        private const val COLUMN_CONTACT_INFO = "contactInfo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_CLIENTS (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_FIRST_NAME TEXT,
                $COLUMN_LAST_NAME TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_AGE TEXT,
                $COLUMN_GENDER TEXT,
                $COLUMN_SCHEDULE TEXT,
                $COLUMN_CONTACT_INFO TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
        onCreate(db)
    }
}

