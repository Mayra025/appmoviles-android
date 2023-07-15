package com.example.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class AnadirContinente : AppCompatActivity() {
    private lateinit var arreglo: MutableList<Continente>
    private lateinit var databaseHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_continente)

        databaseHelper = BaseDatosHelper(this)
        arreglo = databaseHelper.getAllContinentes() as MutableList<Continente>

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_continente)
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_continente)
        botonGuardar
            .setOnClickListener {
                guardarContinente()
            }
        botonCancelar
            .setOnClickListener {
                finish()
            }
    }

    private fun guardarContinente() {
        val nombre = obtenerTexto(R.id.txt_nombre_continente)
        val esHemisferioNorte = obtenerValorBooleano(R.id.chk_hemis)
        val fechaPrimeraCiv = obtenerFecha(R.id.txt_fecha_civ)
        val paises=obtenerTextoInt(R.id.txt_paises)
        val area = obtenerTextoDouble(R.id.txt_area)

        val nuevoContinente = Continente(0,"", false, Date(), 0, 0.0)
        nuevoContinente.crearContinente(nombre, esHemisferioNorte, fechaPrimeraCiv, paises, area)

        val id = databaseHelper.insertContinente(nuevoContinente)
        nuevoContinente.id = id.toInt()
        arreglo.add(nuevoContinente)  // Agregar el nuevo continente a la lista existente en LvContineete
        finish()
    }



    private fun obtenerTexto(id: Int): String {
        val editText = findViewById<EditText>(id)
        return editText.text.toString()
    }
    private fun obtenerTextoInt(id: Int): Int {
        val editText = findViewById<EditText>(id)
        val texto = editText.text.toString()
        return if (texto.isNotEmpty()) texto.toInt() else 0
    }

    private fun obtenerTextoDouble(id: Int): Double {
        val editText = findViewById<EditText>(id)
        val texto = editText.text.toString()
        return if (texto.isNotEmpty()) texto.toDouble() else 0.0
    }

    private fun obtenerValorBooleano(id: Int): Boolean {
        val checkBox = findViewById<CheckBox>(id)
        return checkBox.isChecked
    }

    private fun obtenerFecha(id: Int): Date {
        val editText = findViewById<EditText>(id)
        val fechaString = editText.text.toString()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(fechaString)
    }
}