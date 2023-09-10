package com.example.ex02_app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListvPais : AppCompatActivity() {

    private var idItemSeleccionado = 0
    var arreglo:ArrayList<Pais> = arrayListOf()

    private var cId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listv_pais)

        //Lista
        cId = intent.getStringExtra("continenteId") ?: ""
        arreglo = ArrayList() // Inicializa la lista vacía, ya que Firestore manejará los datos

        val etCont = findViewById<TextView>(R.id.tv_paises)
        etCont.setText("ID continente: "+cId)

        val listView=findViewById<ListView>(R.id.lv_continente)
        val adaptador=ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //como se va a ver (XML)
            arreglo
        )
        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView=findViewById<Button>(
            R.id.btn_anadir_lv_pais)

        botonAnadirListView.setOnClickListener{
            val intent = Intent(this, AnadirPais::class.java)
            intent.putExtra("continenteId", cId)
            startActivity(intent)
        }

        registerForContextMenu(listView)
        cargarDatosDesdeFirestore(adaptador)
    }

    // Cargar datos desde Firestore
    fun cargarDatosDesdeFirestore(adaptador: ArrayAdapter<Pais>){
        val db= Firebase.firestore
        val pRefUnico=db.collection("paises")
        arreglo.clear() //cada consulta se limpia el arreglo
        adaptador.notifyDataSetChanged()
        pRefUnico
            .whereEqualTo("continenteId", cId) // o cualquier otro campo que sea único
            .get()
            .addOnSuccessListener { //it (esto es lo q sea q llegue)
                for(pais in it){
                    pais.id //id quemado o autogenerado
                    anadirAArregloP(pais)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener{   }
    }

    fun anadirAArregloP(p: QueryDocumentSnapshot){
        //ciudad.id
        val nuevo=Pais(
            p.data.get("id") as String?,
            p.data.get("nombre") as String?,
            p.data.get("esCapital") as Boolean?,
            p.data.get("fechaIndependencia") as Timestamp?,
            p.data.get("poblacion") as Long?,
            p.data.get("indiceDH") as Long?,
            p.data.get("continenteId") as String?
        )
        arreglo.add(nuevo)
    }

    //menu de contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater=menuInflater
        inflater.inflate(R.menu.menup,menu)

        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        idItemSeleccionado=id
    }

    //del item seleccionado
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar->{
                val pais = arreglo[idItemSeleccionado]
                val intent = Intent(this, EditarPais::class.java)
                intent.putExtra(EditarPais.EXTRA_PAIS, pais)
                startActivity(intent)
                true
            }
            R.id.mi_eliminar->{
                abrirDialogo()
                true
            }
            else-> super.onContextItemSelected(item)
        }
    }

    //accion al seleccionar Eliminar
    fun abrirDialogo(){
        val builder= AlertDialog.Builder(this)  //constructor de dialogo
        builder.setTitle("Desea eliminar? "+arreglo[idItemSeleccionado].nombre +arreglo[idItemSeleccionado].id)
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which->
                val pais = arreglo[idItemSeleccionado].id
                if (pais != null) {
                    eliminarRegistro(pais)
                }

            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo=builder.create()
        dialogo.show()
    }
    fun eliminarRegistro(id:String){
        val db=Firebase.firestore
        val ref=db.collection("paises")
        ref .document(id)
            .delete()
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    arreglo.removeAt(idItemSeleccionado)
                } else {
                }
            }
            .addOnFailureListener {
            }
    }

    companion object {
        const val EXTRA_PAIS = "pais"
    }
}