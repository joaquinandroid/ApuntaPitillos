package com.example.apuntapitillos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteHelper(context: Context): SQLiteOpenHelper(
    context, "pitillos.db", null, 1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE pitillos " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ano TEXT, mes TEXT, dia TEXT, diaSemana TEXT, hora TEXT, minuto TEXT, intensidad TEXT, situacion TEXT)"
        db!!.execSQL(ordenCreacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS pitillos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun anadirPitillo(ano: String, mes: String, dia: String, diaSemana: String, hora: String, minuto: String,
                      intensidad: String, situacion: String){
        val datos = ContentValues()
        datos.put("ano", ano)
        datos.put("mes", mes)
        datos.put("dia", dia)
        datos.put("diaSemana", diaSemana)
        datos.put("hora", hora)
        datos.put("minuto", minuto)
        datos.put("intensidad", intensidad)
        datos.put("situacion", situacion)

        val db = this.writableDatabase
        db.insert("pitillos", null, datos)
        db.close()
    }
}