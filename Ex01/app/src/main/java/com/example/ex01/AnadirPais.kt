package com.example.ex01

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class AnadirPais : AppCompatActivity() {
    private lateinit var arreglo: MutableList<Pais>
    private lateinit var databaseHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_pais)

        databaseHelper = BaseDatosHelper(this)
        val continentId = intent.getIntExtra("continenteId", 0)
        arreglo = databaseHelper.getPaisesByContinente(continentId) as MutableList<Pais>
        val botonGuardar = findViewById<Button>(R.id.btn_guardar_pais)
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_pais)
        botonGuardar
            .setOnClickListener {
                guardarPais(continentId)
            }
        botonCancelar
            .setOnClickListener {
                finish()
            }
    }

    private fun guardarPais(idC: Int) {
        val nombre = obtenerTexto(R.id.txt_nombre_pais)
        val esCapital = obtenerValorBooleano(R.id.chk_pais)
        val fechaIndep = obtenerFecha(R.id.txt_fecha_ind)
        val poblacion=obtenerTextoInt(R.id.txt_poblacion)
        val idh = obtenerTextoDouble(R.id.txt_idh )

        val nuevoPais = Pais(0,"", false, Date(), 0, 0.0,0)
        nuevoPais.crearPais(nombre, esCapital, fechaIndep,poblacion, idh,idC)

        val id = databaseHelper.insertPais(nuevoPais)
        nuevoPais.id = id.toInt()
        arreglo.add(nuevoPais)

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