package com.example.movilescomp2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class IFirestore : AppCompatActivity() {
    var query: Query?=null
    val arreglo:ArrayList<ICities> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirestore)

        //Configurando el list view
        val listView=findViewById<ListView>(R.id.lv_firestore)
        //efectuar los cambios
        val adaptador=ArrayAdapter(this,android.R.layout.simple_list_item_1,arreglo)
        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()

        //Botones
        val botonDatosPrueba=findViewById<Button>(R.id.btn_fs_datos_prueba)
        botonDatosPrueba.setOnClickListener { crearDatosPrueba() }

        val botonOrderBy=findViewById<Button>(R.id.btn_fs_order_by)
        botonOrderBy.setOnClickListener { consultarConOrderBy(adaptador) }

        val botonObtenerDocumento=findViewById<Button>(R.id.btn_fs_odoc)
        botonObtenerDocumento.setOnClickListener { consultarDocumento(adaptador) }

        val botonIndiceCompuesto=findViewById<Button>(R.id.btn_fs_ind_comp)
        botonIndiceCompuesto.setOnClickListener { consultarIndiceCompuesto(adaptador) }

        //solo se necesita el id
        val botonCrear=findViewById<Button>(R.id.btn_fs_crear)
        botonCrear.setOnClickListener { crearEjemplo() }

        //boton Eiminar
        val botonFirebaseEliminar=findViewById<Button>(R.id.btn_fs_eliminar)
        botonFirebaseEliminar.setOnClickListener{eliminarRegistro()}

        //Empezar Paginar
        val botonFirebaseEmpezarPaginar=findViewById<Button>(R.id.btn_fs_epaginar)
        botonFirebaseEmpezarPaginar.setOnClickListener {
            query=null;
            consultarCiudades(adaptador)
        }

        //Paginar
        val botonFirebasePaginar=findViewById<Button>(R.id.btn_fs_paginar)
        botonFirebasePaginar.setOnClickListener { consultarCiudades(adaptador) }
    }

    fun consultarCiudades(adaptador: ArrayAdapter<ICities>){
        val db=Firebase.firestore
        val citiesRef=db.collection("cities").orderBy("population").limit(1)
        var tarea:Task<QuerySnapshot>? =null

        if(query==null){
            tarea=citiesRef.get()//1era vez
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
        }else   {
            //consulta de la consulta anterior , empezando en el nuevo doc
            tarea=query!!.get()
        }

        if(tarea != null){
            tarea
                .addOnSuccessListener {
                    documentSnapshots -> guardarQuery(documentSnapshots,citiesRef)
                    for (ciudad in documentSnapshots){
                        anadirAArregloCiudad(ciudad)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    //si falla
                }
        }
    }

    fun guardarQuery(documentSnapshots: QuerySnapshot, refCities: Query){
        if(documentSnapshots.size()>0){
            val ultimoDocumento=documentSnapshots.documents[documentSnapshots.size()-1]

            query=refCities
                    //startAfter (empezar despues) permite paginar
                .startAfter(ultimoDocumento)
        }
    }




    fun eliminarRegistro(){
        val db=Firebase.firestore
        val referenciaEjemploEstudiante=db.collection("ejemplo")
        referenciaEjemploEstudiante
            .document("12345678")
            .delete()
            .addOnCompleteListener{
                //si sale bien
            }
            .addOnFailureListener{
                //Si sale mal
            }
    }

    fun crearEjemplo(){
        val db=Firebase.firestore
        val referenciaEjemploEstudiante=db.collection("ejemplo")
        //.document("id_hijo")
        //.collection("estudiante")

        val datosEstudiante= hashMapOf(  //hashmap==json
            "nombre" to "mayra",
            "graduado" to "false",
            "promedio" to 15.0,
            "direccion" to hashMapOf(
                "direccion" to "Llano Grande",
                "numeroCalle" to 1234
            ),
            "materias" to listOf("web", "moviles")
        )

        //Identificador quemado (cerar/actualizar)
        referenciaEjemploEstudiante
            .document("12345678")
            .set(datosEstudiante)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }

        //Identificador quemado pero autogenerado con Date().time
        val identificador= Date().time
        referenciaEjemploEstudiante  //cerar/actualizar
            .document(identificador.toString())
            .set(datosEstudiante)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }

        //Sin Idnetificador (crear)
        referenciaEjemploEstudiante
            .add(datosEstudiante)
            .addOnCompleteListener {  }
            .addOnFailureListener {  }

    }

    fun consultarIndiceCompuesto(adaptador: ArrayAdapter<ICities>){
        val db=Firebase.firestore
        val citiesRefUnico=db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico
            .whereEqualTo("capital",false)
            .whereLessThanOrEqualTo("population",4000000)
            .orderBy("population", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                for (ciudad in it){
                    anadirAArregloCiudad(ciudad)

                }
                adaptador.notifyDataSetChanged()
            }
            .addOnSuccessListener {
                //error
            }
    }

    fun consultarDocumento(adaptador: ArrayAdapter<ICities>){
        val db=Firebase.firestore
        val citiesRefUnico=db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        citiesRefUnico.document("BJ").get() //obtener 1 DOCUMENTO
            .addOnSuccessListener { //it-> ES UN OBJETO
            arreglo.add(
                ICities(it.data?.get("name") as String?,
                        it.data?.get("state") as String?,
                        it.data?.get("country") as String?,
                        it.data?.get("capital") as Boolean?,
                        it.data?.get("population") as Long?,
                        it.data?.get("regions") as ArrayList<String>?,
                )
            )
                adaptador.notifyDataSetChanged()
            }
            .addOnSuccessListener {
                //errores
            }
    }

    fun consultarConOrderBy(adaptador: ArrayAdapter<ICities>){
        val db=Firebase.firestore
        val citiesRefUnico=db.collection("cities")
        limpiarArreglo() //cada consulta se limpia el arreglo
        adaptador.notifyDataSetChanged()
        citiesRefUnico
            .orderBy("population", Query.Direction.ASCENDING) //campo a ordenar, direccion
            .get()  //para hacer la llamada a
            .addOnSuccessListener { //it (esto es lo q sea q llegue)
                for(ciudad in it){
                    ciudad.id //id quemado o autogenerado
                    anadirAArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener{
                //Errores
            }

    }

    fun anadirAArregloCiudad(ciudad:QueryDocumentSnapshot){
        //ciudad.id
        val nuevaCiudad=ICities(
            ciudad.data.get("name") as String?,
            ciudad.data.get("state") as String?,
            ciudad.data.get("country") as String?,
            ciudad.data.get("capital") as Boolean?,
            ciudad.data.get("population") as Long?,
            ciudad.data.get("regions") as ArrayList<String>?,

            )
        arreglo.add(nuevaCiudad)

    }

    fun limpiarArreglo(){
        arreglo.clear()
    }

    fun crearDatosPrueba(){
        val db= Firebase.firestore

        //Datos de de la web
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal"),
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal"),
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast"),
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu"),
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei"),
        )
        cities.document("BJ").set(data5)
    }

}