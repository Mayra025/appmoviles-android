package com.example.d02_sqlitee

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class ListvPais : AppCompatActivity() {
    private lateinit var arreglo: MutableList<Pais>
    private lateinit var listView: ListView
    private lateinit var adaptador: ArrayAdapter<Pais>
    private var idItemSeleccionado = 0
    private lateinit var databaseHelper: BaseDatosHelper
    private var continenteId = 0
    private var name=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listv_pais)

        //Lista
        databaseHelper = BaseDatosHelper(this)
        continenteId = intent.getIntExtra("continenteId", 0)
        name = intent.getStringExtra("nameC") ?: ""
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

        val etCont = findViewById<TextView>(R.id.tv_paises)
        etCont.text = "Continente: $name"

        // Crear -> 'escuchar' al botón
        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_lv_pais)
        botonAnadirListView.setOnClickListener {
            val intent = Intent(this, AnadirPais::class.java)
            intent.putExtra("continenteId", continenteId)
            startActivity(intent)
        }

        // Editar, Eliminar, Ver -> llamar al registro del menú
        registerForContextMenu(listView)
    }

    // Llamar a cargarDatosDesdeBaseDeDatos() después de agregar o eliminar un país
    override fun onResume() {
        super.onResume()
        cargarDatosDesdeBaseDeDatos()
    }

    private fun cargarDatosDesdeBaseDeDatos() {
        arreglo.clear()
        arreglo.addAll(databaseHelper.getPaisesByContinente(continenteId))
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
        inflater.inflate(R.menu.menup,menu)

        //obtener el id de ArrayListSeleccionado
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
                return true
            }
            else-> super.onContextItemSelected(item)
        }
    }

    //accion al seleccionar Eliminar
    fun abrirDialogo(){
        val builder= AlertDialog.Builder(this)  //constructor de dialogo
        builder.setTitle("Desea eliminar? "+arreglo[idItemSeleccionado].nombre)
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which->
                val pais = arreglo[idItemSeleccionado]
                databaseHelper.deletePais(pais.id)
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

    companion object {
        private const val EXTRA_PAIS = "pais"
    }
}