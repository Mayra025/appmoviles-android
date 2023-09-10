package com.example.ex02_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class EditarContinente : AppCompatActivity() {
    private lateinit var botonGuardar: Button
    private lateinit var botonCancelar: Button
    private lateinit var continente: Continente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_continente)

        continente = intent.getParcelableExtra(EXTRA_CONTINENTE)!!  // para obtener un objeto Parcelable de un Intent. El operador !!  para forzar el objeto obtenido a ser no nulo,

        // Inicializar los campos de texto con los datos del continente
        val etNombre = findViewById<EditText>(R.id.txt_nombre_continente)
        val cbHemisferioNorte = findViewById<CheckBox>(R.id.chk_hemis)
        val etFechaPrimeraCiv = findViewById<EditText>(R.id.txt_fecha_civ)
        val etCantidadPaises = findViewById<EditText>(R.id.txt_paises)
        val etArea = findViewById<EditText>(R.id.txt_area)

        etNombre.setText(continente.nombre)
        cbHemisferioNorte.isChecked = continente.esHemisferioNorte ?:false

        // Asignar el valor de la fecha al campo de texto, teniendo en cuenta el formato deseado
       val fechaCivilizacion = continente.fechaCivilizacion

        if (fechaCivilizacion != null) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaString = dateFormat.format(fechaCivilizacion.toDate()) // Convierte Timestamp a Date
            etFechaPrimeraCiv.setText(fechaString)
        } else {
            etFechaPrimeraCiv.text.clear() // Limpia el EditText si la fecha es nula
        }

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
    }

    private fun guardarContinente() {
        // Actualizar los valores del continente con los datos ingresados en los campos de texto
        continente.nombre = obtenerTexto(R.id.txt_nombre_continente)
        continente.esHemisferioNorte = obtenerValorBooleano(R.id.chk_hemis)
        continente.fechaCivilizacion = obtenerFecha(R.id.txt_fecha_civ)
        continente.cantidadPaises = obtenerTextoLong(R.id.txt_paises)
        continente.area = obtenerTextoLong(R.id.txt_area)

        val db= Firebase.firestore
        val continenteId = continente.id

        if (continenteId != null) {
            // Crear un mapa de datos solo con los campos que deseas actualizar
            val actualizaciones = hashMapOf<String, Any?>()
                actualizaciones["nombre"] = continente.nombre
                actualizaciones["esHemisferioNorte"] = continente.esHemisferioNorte
                actualizaciones["fechaCivilizacion"] = continente.fechaCivilizacion
                actualizaciones["cantidadPaises"] = continente.cantidadPaises
                actualizaciones["area"] = continente.area

            db.collection("continentes")
                .document(continenteId)
                .update(actualizaciones)
                .addOnSuccessListener {
                    // La actualización fue exitosa
                    finish()
                }
                .addOnFailureListener { e ->
                    // Ocurrió un error durante la actualización
                    // Manejar el error según sea necesario
                }

        } else {
            // Manejar el caso en el que el ID del documento sea nulo
        }
    }

    private fun obtenerTexto(id: Int): String {
        val editText = findViewById<EditText>(id)
        return editText.text.toString()
    }
    private fun obtenerTextoLong(id: Int): Long {
        val editText = findViewById<EditText>(id)
        val texto = editText.text.toString()
        return if (texto.isNotEmpty()) texto.toLong() else 0L
    }

    private fun obtenerValorBooleano(id: Int): Boolean {
        val checkBox = findViewById<CheckBox>(id)
        return checkBox.isChecked
    }

    private fun obtenerFecha(id: Int): Timestamp {
        val editText = findViewById<EditText>(id)
        val fechaString = editText.text.toString()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(fechaString)
        return Timestamp(date)
    }

    companion object {
        const val EXTRA_CONTINENTE = "continente" // Mismo nombre de constante que en ListvContinente
    }
}