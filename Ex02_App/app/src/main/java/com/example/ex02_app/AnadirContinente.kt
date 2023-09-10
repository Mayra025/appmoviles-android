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

class AnadirContinente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_continente)

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_continente)
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_continente)

        botonGuardar.setOnClickListener {
            guardarContinente()
        }

        botonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun guardarContinente() {
        val nombre = obtenerTexto(R.id.txt_nombre_continente)
        val esHemisferioNorte = obtenerValorBooleano(R.id.chk_hemis)
        val fechaPrimeraCiv = obtenerFecha(R.id.txt_fecha_civ)
        val paises=obtenerTextoLong(R.id.txt_paises)
        val area = obtenerTextoLong(R.id.txt_area)

        val db= Firebase.firestore
        val id= Date().time
        val conts = db.collection("continentes")

        val data1 = hashMapOf(
            "nombre" to nombre,
            "esHemisferioNorte" to esHemisferioNorte,
            "fechaCivilizacion" to fechaPrimeraCiv,
            "cantidadPaises" to paises,
            "area" to area
        )

        conts.document(id.toString()).set(data1)
            .addOnSuccessListener {
                finish()
            }
            .addOnFailureListener {  }

        conts.document(id.toString()).update("id",id.toString())
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

        // Convierte la fecha de tipo Date a Timestamp
        return Timestamp(date)
    }
}