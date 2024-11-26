package com.example.apuntapitillos

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.print.PrintManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.print.PrintHelper
import com.example.apuntapitillos.databinding.ActivityInformeBinding

class InformeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformeBinding
    lateinit var pitillosHelper: MySQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityInformeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConsulta.setOnClickListener {
            consulta()
        }

        binding.btnVolver.setOnClickListener {
           // val intentVolver: Intent = Intent(this, MainActivity::class.java)
            //startActivity(intentVolver)
            finish()
        }

    }

    fun consulta() {

        pitillosHelper = MySQLiteHelper(this)

        binding.txVcontenido.text = ""
        val db: SQLiteDatabase = pitillosHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM pitillos", null)

        if (cursor.moveToFirst()) {
            do {
                binding.txVcontenido.append(cursor.getInt(0).toString() + "- ")
                binding.txVcontenido.append(cursor.getString(4).toString() + " ")
                binding.txVcontenido.append(cursor.getString(3).toString() + " ")
                binding.txVcontenido.append(cursor.getString(2).toString() + " ")
                binding.txVcontenido.append(cursor.getString(1).toString() + " ")
                binding.txVcontenido.append(cursor.getString(5).toString() + ": ")
                binding.txVcontenido.append(cursor.getString(6).toString() + "\n" + "intensidad= ")
                binding.txVcontenido.append(cursor.getString(7).toString() + "\n" + "situacion= ")
                binding.txVcontenido.append(cursor.getString(8).toString() + "\n\n")

            } while (cursor.moveToNext())
        }


    }
}