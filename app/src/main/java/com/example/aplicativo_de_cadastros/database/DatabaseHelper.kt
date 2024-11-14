package com.example.aplicativo_de_cadastros.database;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aplicativo_de_cadastros.models.Item

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ItemDB"
        private const val TABLE_ITEMS = "Items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_ITEMS ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        onCreate(db)
    }

    // Create
    fun addItem(name: String, description: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.insert(TABLE_ITEMS, null, contentValues)
    }

    // Read
    fun getItems(): List<Item> {
        val itemList = mutableListOf<Item>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_ITEMS"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val item = Item(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                )
                itemList.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    // Update
    fun updateItem(id: Int, name: String, description: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.update(TABLE_ITEMS, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    // Delete
    fun deleteItem(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_ITEMS, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}