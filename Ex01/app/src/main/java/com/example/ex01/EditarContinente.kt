package com.example.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class EditarContinente : AppCompatActivity() {
    private lateinit var botonGuardar: Button
    private lateinit var botonCancelar: Button
    private lateinit var continente: Continente
    private lateinit var databaseHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_continente)

        continente = intent.getParcelableExtra(EXTRA_CONTINENTE)!!  // para obtener un objeto Parcelable de un Intent. El operador !!  para forzar el objeto obtenido a ser no nulo,
        // Inicializar los campos de texto con los datos del continente
        val etNombre = findViewById<EditText>(R.id.txt_nombre_continente)
        val cbHemisferioNorte = findViewById<CheckBox>(R.id.chk_hemis)
        val etFechaPrimeraCiv = findViewById<EditText>(R.id.txt_fecha_civ)
        val etCantidadPaises = findViewById<EditText>(R.id.txt_paises)
        val etArea = findViewById<EditText>(R.id.txt_area)

        etNombre.setText(continente.nombre)
        cbHemisferioNorte.isChecked = continente.esHemisferioNorte
        // Asignar el valor de la fecha al campo de texto, teniendo en cuenta el formato deseado
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaString = dateFormat.format(continente.fechaCivilizacion)
        etFechaPrimeraCiv.setText(fechaString)
        etCantidadPaises.setText(continente.cantidadPaises.toString())
        etArea.setText(continente.area.toString())

        botonGuardar = findViewById(R.id.btn_guardar_continente)
        botonCancelar = findViewById(R.id.btn_cancelar_continente)

        botonGuardar.setOnClickListener {
            guardarContinente()
        }

        botonCancelar.setOnClickListener {
            finish()
        }
        databaseHelper = BaseDatosHelper(this)
    }

    private fun guardarContinente() {
        // Actualizar los valores del continente con los datos ingresados en los campos de texto
        continente.nombre = obtenerTexto(R.id.txt_nombre_continente)
        continente.esHemisferioNorte = obtenerValorBooleano(R.id.chk_hemis)
        continente.fechaCivilizacion = obtenerFecha(R.id.txt_fecha_civ)
        continente.cantidadPaises = obtenerTextoInt(R.id.txt_paises)
        continente.area = obtenerTextoDouble(R.id.txt_area)

        databaseHelper.updateContinente(continente)

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
        const val EXTRA_CONTINENTE = "continente" // Mismo nombre de constante que en ListvContinente
    }
}