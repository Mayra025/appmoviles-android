package com.example.movilescomp2023a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {  //viene de AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //hereda del padre
        setContentView(R.layout.activity_main) // R=Resource, activity_main=interfaz

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener {
                irActividad(AACicloVida::class.java)
            }

        val botonListView=findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener{
                irActividad(BListView::class.java)
            }

    }


    fun irActividad( //metodo de la  clase
        clase:Class<*>
    ){
        val intent =Intent(this,clase)
        startActivity(intent)
    }

}