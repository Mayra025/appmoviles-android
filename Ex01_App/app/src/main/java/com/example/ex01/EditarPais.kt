package com.example.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class EditarPais : AppCompatActivity() {
    private lateinit var botonGuardar: Button
    private lateinit var botonCancelar: Button
    private lateinit var pais: Pais
    private lateinit var databaseHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_pais)

        pais = intent.getParcelableExtra(EXTRA_PAIS)!!
        // Inicializar los campos de texto con los datos
        val etNombre = findViewById<EditText>(R.id.txt_nombre_pais)
        val cbCapital = findViewById<CheckBox>(R.id.chk_pais)
        val etFechaInd = findViewById<EditText>(R.id.txt_fecha_ind)
        val etPoblacion = findViewById<EditText>(R.id.txt_poblacion)
        val etIdh= findViewById<EditText>(R.id.txt_idh)

        etNombre.setText(pais.nombre)
        cbCapital.isChecked = pais.esCapital
        // Asignar el valor de la fecha al campo de texto, teniendo en cuenta el formato deseado
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaString = dateFormat.format(pais.fechaIndependencia)
        etFechaInd.setText(fechaString)
        etPoblacion.setText(pais.poblacion.toString())
        etIdh.setText(pais.indiceDH.toString())

        botonGuardar = findViewById(R.id.btn_guardar_pais)
        botonCancelar = findViewById(R.id.btn_cancelar_pais )

        botonGuardar.setOnClickListener {
            guardarPais()
        }

        botonCancelar.setOnClickListener {
            finish()
        }
        databaseHelper = BaseDatosHelper(this)
    }

    private fun guardarPais() {
        // Actualizar los valores del continente con los datos ingresados en los campos de texto
        pais.nombre = obtenerTexto(R.id.txt_nombre_pais)
        pais.esCapital = obtenerValorBooleano(R.id.chk_pais)
        pais.fechaIndependencia = obtenerFecha(R.id.txt_fecha_ind)
        pais.poblacion = obtenerTextoInt(R.id.txt_poblacion)
        pais.indiceDH = obtenerTextoDouble(R.id.txt_idh)

        databaseHelper.updatePais(pais)

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

    companion object {
        const val EXTRA_PAIS = "pais" // Mismo nombre de constante que en ListvPais
    }
}