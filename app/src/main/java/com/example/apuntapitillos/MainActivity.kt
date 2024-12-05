package com.example.apuntapitillos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.apuntapitillos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var pitillosHelper: MySQLiteHelper

    var contador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pitillosHelper = MySQLiteHelper(this)

        binding.btnCerrarApp.setOnClickListener{
            finish()
        }

        binding.btnReiniciar.setOnClickListener {
            val db: SQLiteDatabase = pitillosHelper.writableDatabase
            //val borradoTabla = "DELETE FROM pitillos"
            val borrado = "DROP TABLE pitillos"
            val reinicializarId =  "CREATE TABLE pitillos " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ano TEXT, mes TEXT, dia TEXT, diaSemana TEXT, hora TEXT, minuto TEXT, intensidad TEXT, situacion TEXT)"
            //db.execSQL(borradoTabla)
            db.execSQL(borrado)
            db.execSQL(reinicializarId)
        }

        binding.btnInsertar.setOnClickListener {

            if (binding.edTintensidad.text.isNotBlank() && binding.edTsituacion.text.isNotBlank()) {
                contador++
                var mesTexto: String = ""
                var diaSemanaTexto: String = ""

                val momento = Calendar.getInstance()
                val momentoTexto = momento.toString()
                val ano = momento.get(Calendar.YEAR).toString()
                val mes = momento.get(Calendar.MONTH).toString()
                when (mes.toString()) {
                    "0" -> mesTexto = "enero"
                    "1" -> mesTexto = "febrero"
                    "2" -> mesTexto = "marzo"
                    "3" -> mesTexto = "abril"
                    "4" -> mesTexto = "mayo"
                    "5" -> mesTexto = "junio"
                    "6" -> mesTexto = "julio"
                    "7" -> mesTexto = "agosto"
                    "8" -> mesTexto = "septiembre"
                    "9" -> mesTexto = "octubre"
                    "10" -> mesTexto = "noviembre"
                    "11" -> mesTexto = "diciembre"
                }
                val dia = momento.get(Calendar.DAY_OF_MONTH).toString()
                val diasemana = momento.get(Calendar.DAY_OF_WEEK)
                when (diasemana.toString()) {
                    "1" -> diaSemanaTexto = "domingo"
                    "2" -> diaSemanaTexto = "lunes"
                    "3" -> diaSemanaTexto = "martes"
                    "4" -> diaSemanaTexto = "miercoles"
                    "5" -> diaSemanaTexto = "jueves"
                    "6" -> diaSemanaTexto = "viernes"
                    "0" -> diaSemanaTexto = "sabado"
                }
                val hora = momento.get(Calendar.HOUR_OF_DAY).toString()
                val minuto = momento.get(Calendar.MINUTE).toString()
                val intensidad: String = binding.edTintensidad.text.toString()
                val situacion: String = binding.edTsituacion.text.toString()

                if (intensidad == "1" || intensidad == "2" || intensidad == "3" || intensidad == "4") {
                    pitillosHelper.anadirPitillo(
                        ano,
                        mesTexto,
                        dia,
                        diaSemanaTexto,
                        hora,
                        minuto,
                        intensidad,
                        situacion
                    )
                    binding.edTintensidad.text.clear()
                    binding.edTsituacion.text.clear()
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("En intensidad tienes que indicar")
                    builder.setMessage("1, 2, 3 o 4")
                    builder.setPositiveButton("Continuar") { dialogInterface, witch ->
                        binding.edTintensidad.text.clear()
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
                Toast.makeText(this, "guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "no se han guardado datos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnInformar.setOnClickListener {
            val intentInformar: Intent = Intent(this, InformeActivity::class.java)
            startActivity(intentInformar)
        }
    }
}