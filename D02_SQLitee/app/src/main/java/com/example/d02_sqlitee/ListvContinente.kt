package com.example.d02_sqlitee

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class ListvContinente : AppCompatActivity() {
    private lateinit var arreglo: MutableList<Continente>
    private lateinit var listView: ListView
    private lateinit var adaptador: ArrayAdapter<Continente>
    private var idItemSeleccionado = 0
    private lateinit var databaseHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listv_continente)

        //Lista
        databaseHelper = BaseDatosHelper(this)
        arreglo = mutableListOf()

        adaptador = ArrayAdapter(
            this, // contexto
            android.R.layout.simple_list_item_1, // cómo se va a ver (XML)
            arreglo
        )
        listView = findViewById<ListView>(R.id.lv_continente)
        listView.adapter = adaptador

        // Cargar los datos desde la base de datos al iniciar
        cargarDatosDesdeBaseDeDatos()


        // Crear -> 'escuchar' al botón
        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_lv_continente)
        botonAnadirListView.setOnClickListener {
            val intent = Intent(this, AnadirContinente::class.java)
            startActivity(intent)
        }

        // Editar, Eliminar, Ver -> llamar al registro del menú
        registerForContextMenu(listView)

        /*arreglo  = databaseHelper.getAllContinentes() as MutableList<Continente>
        listView=findViewById<ListView>(R.id.lv_continente)
        adaptador=ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //como se va a ver (XML)
            arreglo
        )
        listView.adapter=adaptador

        //Crear -> 'escuchar' al boton
        val botonAnadirListView=findViewById<Button>(
            R.id.btn_anadir_lv_continente)
        botonAnadirListView
            .setOnClickListener{
                val intent = Intent(this, AnadirContinente::class.java)
                startActivity(intent)
            }

        //Editar, Eliminar, Ver -> llamar al registro menu
        registerForContextMenu(listView)

       // adaptador.notifyDataSetChanged()*/
    }

    override fun onResume() {
        super.onResume()
        cargarDatosDesdeBaseDeDatos()
    }

    private fun cargarDatosDesdeBaseDeDatos() {
        arreglo.clear()
        arreglo.addAll(databaseHelper.getAllContinentes() as MutableList<Continente>)
        adaptador.notifyDataSetChanged()
    }


    //menu de contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenar opciones del menu
        val inflater=menuInflater
        inflater.inflate(R.menu.menuc,menu)

        //obtener el id de ArrayListSeleccionado
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        idItemSeleccionado=id
    }

    //del item seleccionado
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar->{
                val continente = arreglo[idItemSeleccionado]
                val intent = Intent(this, EditarContinente::class.java)
                intent.putExtra(EditarContinente.EXTRA_CONTINENTE, continente)
                startActivity(intent)
                adaptador.notifyDataSetChanged()
                true
            }
            R.id.mi_eliminar->{
                abrirDialogo()
                return true
            }
            R.id.mi_ver->{
                val intent = Intent(this, ListvPais::class.java)
                intent.putExtra("continenteId", arreglo[idItemSeleccionado].id)
                intent.putExtra("nameC", arreglo[idItemSeleccionado].nombre)

                startActivity(intent)
                adaptador.notifyDataSetChanged()
                true
            }
            else-> super.onContextItemSelected(item)
        }
    }

    //accion al seleccionar Eliminar
    fun abrirDialogo(){
        val builder= AlertDialog.Builder(this)  //constructor de dialogo
        builder.setTitle("Desea eliminar? " +arreglo[idItemSeleccionado].nombre)
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which->
                databaseHelper.deleteContinente(arreglo[idItemSeleccionado].id)
                arreglo.removeAt(idItemSeleccionado)
                adaptador.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo=builder.create()
        dialogo.show()
    }

    companion object { //instancias estàticas
        private const val EXTRA_CONTINENTE = "continente" //Esta constante se utiliza como clave para pasar datos extras a través de un Intent al iniciar una actividad.
    }

}