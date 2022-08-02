package com.example.pokedex_samaritan

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pokedex_samaritan.models.Captured

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                DATE_COL + " TEXT," +
                LVL_COL + " TEXT," +
                IMG_COL + " TEXT," +
                HEX_COL + " TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPokemon(name : String, date : String, level : String, image : String, hex : String){
        val values = ContentValues()

        values.put(NAME_COl, name)
        values.put(DATE_COL, date)
        values.put(LVL_COL, level)
        values.put(IMG_COL, image)
        values.put(HEX_COL, hex)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getPokemon(): ArrayList<Captured> {
        val pokemon = ArrayList<Captured>()
        val db = this.readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(NAME_COl))
                    val date = cursor.getString(cursor.getColumnIndex(DATE_COL))
                    val lvl = cursor.getString(cursor.getColumnIndex(LVL_COL))
                    val img = cursor.getString(cursor.getColumnIndex(IMG_COL))
                    val hex = cursor.getString(cursor.getColumnIndex(HEX_COL))

                    val c = Captured(name,date,lvl,img,hex)
                    pokemon.add(c)
                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return pokemon
    }

    companion object{
        private const val DATABASE_NAME = "Captured_Pokemon"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "captured_table"
        const val ID_COL = "id"
        const val NAME_COl = "nickname"
        const val DATE_COL = "date"
        const val LVL_COL = "level"
        const val IMG_COL = "img"
        const val HEX_COL = "hex"
    }
}