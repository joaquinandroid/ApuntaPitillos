package com.example.apuntapitillos

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apuntapitillos.databinding.ActivityInformeBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


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
            finish()
        }

        binding.btnPdf.setOnClickListener {
           /* var texto = binding.txVcontenido.text.toString()
            abrirDirectorio(Uri.parse("/storage/emulated/0/Documents"))
            guardarTextoEnArchivo(texto, "informe.txt")*/

            mostrarAlertDialog(this)
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

    private fun guardarTextoEnArchivo(contenido: String, nombreArchivo: String) {
        val estadoAlmacenamiento = Environment.getExternalStorageState()

        if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED) {
            val directorio = "/storage/emulated/0/Documents"

            val archivo = File(directorio, nombreArchivo)
            if(archivo.exists()){
                archivo.delete()
            }

            try {
                val flujoSalida = FileOutputStream(archivo, true)
                val writer = OutputStreamWriter(flujoSalida)
                writer.append(contenido)
                writer.close()

                Toast.makeText(
                    this,
                    "Texto añadido en $directorio $nombreArchivo",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No se pudo acceder al almacenamiento externo", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun abrirDirectorio(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        startActivity(intent)
    }

    fun mostrarAlertDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡ey!")
            .setMessage("Si no lo encuentras, el archivo está en la carpeta Documents")
            .setPositiveButton("OK"){ dialog, which ->
                var texto = binding.txVcontenido.text.toString()
                abrirDirectorio(Uri.parse("/storage/emulated/0/Documents"))
                guardarTextoEnArchivo(texto, "informe.txt")
                Toast.makeText(context, "Hecho", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
}