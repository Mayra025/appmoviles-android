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

class AnadirPais : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_pais)

        val continentId = intent.getStringExtra("continenteId") // Cambiamos el tipo de dato a String

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_pais)
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_pais)

        botonGuardar.setOnClickListener {
            if (continentId != null) {
                guardarPais(continentId)
            }
        }

        botonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun guardarPais(idC: String) {
        val nombre = obtenerTexto(R.id.txt_nombre_pais)
        val esCapital = obtenerValorBooleano(R.id.chk_pais)
        val fechaIndep = obtenerFecha(R.id.txt_fecha_ind)
        val poblacion=obtenerTextoLong(R.id.txt_poblacion)
        val idh = obtenerTextoLong(R.id.txt_idh )

        val db= Firebase.firestore
        val id= Date().time
        val conts = db.collection("paises")

        val data1 = hashMapOf(
            "nombre" to nombre,
            "esCapital" to esCapital,
            "fechaIndependencia" to fechaIndep,
            "poblacion" to poblacion,
            "indiceDH" to idh,
            "continenteId" to idC
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
        return Timestamp(date)
    }
}