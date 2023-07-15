package com.example.movilescomp2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView : AppCompatActivity() {
    var totalLikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view)

        inicializarRecyclerView()
    }

    fun aumentarTotalLikes(){
        totalLikes=totalLikes+1
        val totalLikesTextView=findViewById<TextView>(R.id.tv_total_likes)
        totalLikesTextView.text=totalLikes.toString()
    }

    fun inicializarRecyclerView(){
        val recyclerView=findViewById<RecyclerView>(R.id.rv_entrenadores)
        val adaptador=FRecyclerViewAdaptadorNombreDescripcion(
            this,
            BBaseDatosMemoria.arregloBEntrenador,
            recyclerView
        )
        recyclerView.adapter=adaptador
        recyclerView.itemAnimator=androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager=androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}