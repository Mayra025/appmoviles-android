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

class EditarPais : AppCompatActivity() {
    private lateinit var botonGuardar: Button
    private lateinit var botonCancelar: Button
    private lateinit var pais: Pais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pais)

        pais = intent.getParcelableExtra(EXTRA_PAIS)!!

        // Inicializar los campos de texto con los datos
        val etNombre = findViewById<EditText>(R.id.txt_nombre_pais)
        val cbCapital = findViewById<CheckBox>(R.id.chk_pais)
        val etFechaInd = findViewById<EditText>(R.id.txt_fecha_ind)
        val etPoblacion = findViewById<EditText>(R.id.txt_poblacion)
        val etIdh= findViewById<EditText>(R.id.txt_idh)

        etNombre.setText(pais.nombre)
        cbCapital.isChecked = pais.esCapital ?:false

       // Asignar el valor de la fecha al campo de texto, teniendo en cuenta el formato deseado
        val fechaIndependencia=pais.fechaIndependencia
        if(fechaIndependencia!=null){
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaString = dateFormat.format(fechaIndependencia.toDate())
            etFechaInd.setText(fechaString)
        }else {
            etFechaInd.text.clear() // Limpia el EditText si la fecha es nula
        }

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
    }

    private fun guardarPais() {
        // Actualizar los valores del continente con los datos ingresados en los campos de texto
       pais.nombre = obtenerTexto(R.id.txt_nombre_pais)
        pais.esCapital = obtenerValorBooleano(R.id.chk_pais)
        pais.fechaIndependencia = obtenerFecha(R.id.txt_fecha_ind)
        pais.poblacion = obtenerTextoLong(R.id.txt_poblacion)
        pais.indiceDH = obtenerTextoLong(R.id.txt_idh)

        val db= Firebase.firestore
        val paisId=pais.id

        if (paisId != null) {
            val actualizaciones = hashMapOf<String, Any?>()
            actualizaciones["nombre"] = pais.nombre
            actualizaciones["esCapital"] = pais.esCapital
            actualizaciones["fechaIndependencia"] = pais.fechaIndependencia
            actualizaciones["poblacion"] = pais.poblacion
            actualizaciones["indiceDH"] = pais.indiceDH

            db.collection("paises")
                .document(paisId)
                .update(actualizaciones)
                .addOnSuccessListener { finish() }
                .addOnFailureListener { }
        }else{

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
        const val EXTRA_PAIS = "pais" // Mismo nombre de constante que en ListvPais
    }
}