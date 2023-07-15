package com.example.movilescomp2023a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion (
    private val contexto:FRecyclerView,
    private val lista:ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder>(){
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val nombreTextView:TextView
        val descripcionTextView:TextView
        val likesTextVew:TextView
        val accionButton: Button
        var numeroLikes=0
        init{
            nombreTextView=view.findViewById(R.id.tv_nombre)
            descripcionTextView=view.findViewById(R.id.tv_descripcion)
            likesTextVew=view.findViewById(R.id.tv_likes)
            accionButton=view.findViewById<Button>(R.id.btn_dar_like)
            accionButton.setOnClickListener { anadirLike() }

        }

        //funcion'heredada' del padre
        fun anadirLike(){
            numeroLikes=numeroLikes+1
            likesTextVew.text=numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
    }

    //Setear el layout a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater //para usar usar el layout_vista, idk
            .from(parent.context).inflate(R.layout.recycler_view_vista,parent,false)

        return MyViewHolder(itemView)
    }

    //setear datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //se ejcuta una vez se haya
        val entrenadorActual=this.lista[position]
        holder.nombreTextView.text=entrenadorActual.nombre
        holder.descripcionTextView.text=entrenadorActual.descripcion
        holder.likesTextVew.text="0"
        holder.accionButton.text="ID: ${entrenadorActual.id} Nombre: ${entrenadorActual.nombre}"

    }

    //tamaño del arreglo
    override fun getItemCount(): Int {
        return this.lista.size
    }
}