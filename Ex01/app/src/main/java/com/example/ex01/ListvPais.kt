package com.example.ex01

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listv_pais)

        //Lista
        databaseHelper = BaseDatosHelper(this)
        continenteId = intent.getIntExtra("continenteId", 0)
        arreglo = databaseHelper.getPaisesByContinente(continenteId) as MutableList<Pais>

        val etCont = findViewById<TextView>(R.id.tv_paises)
        etCont.setText("ID continente: "+continenteId)

        listView=findViewById<ListView>(R.id.lv_continente)
        adaptador=ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //como se va a ver (XML)
            arreglo
        )
        listView.adapter = adaptador

        //Crear -> 'escuchar' al boton
        val botonAnadirListView=findViewById<Button>(
            R.id.btn_anadir_lv_pais)
        botonAnadirListView
            .setOnClickListener{
                val intent = Intent(this, AnadirPais::class.java)
                intent.putExtra("continenteId", continenteId)
                startActivity(intent)
            }

        //Editar, Eliminar, Ver -> llamar al registro menu
        registerForContextMenu(listView)

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