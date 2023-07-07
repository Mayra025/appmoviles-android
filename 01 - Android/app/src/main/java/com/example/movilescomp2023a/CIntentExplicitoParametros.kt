package com.example.movilescomp2023a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre=intent.getStringExtra("nombre")
        val apellido=intent.getStringExtra("apellido")
        val edad=intent.getIntExtra("edad",0)

        //para boton Intent
        val boton=findViewById<Button>(R.id.btn_devolver_respuesta)
    }

    //para el boton capte el Intent
    fun devolverRespuesta(){
        val intentDevolverParametros= Intent()
        intentDevolverParametros.putExtra("nombreModificado","Vicente")
        intentDevolverParametros.putExtra("edadModificado",33)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

}