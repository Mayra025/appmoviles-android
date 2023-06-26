package com.example.movilescomp2023a

import android.content.DialogInterface
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

class BListView : AppCompatActivity() {
    //arreglo
    val arreglo=BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        //
        val listView=findViewById<ListView>(R.id.lv_list_view)
        val adaptador=ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //como se va a ver (XML)
            arreglo
        )

        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()

        //'escuchar' al boton
        val botonAnadirListView=findViewById<Button>(
            R.id.btn_anadir_list_view)

        botonAnadirListView
            .setOnClickListener{
                anadirEntrenador(adaptador)
            }
        //llamar al registro del menu
        registerForContextMenu(listView)
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
        inflater.inflate(R.menu.menu,menu)

        //obtener el id de ArrayListSeleccionado
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        idItemSeleccionado=id
    }

    //del item seleccionado
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar->{
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar->{
                "${idItemSeleccionado}"
                //llamar a abrirDialogo
                abrirDialogo()
                return true
            }
            else-> super.onContextItemSelected(item)
        }
    }

    //accion al seleccionar un item
    fun abrirDialogo(){
        val builder=AlertDialog.Builder(this)  //constructor de dialogo
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{dialog,which->
                //si Acepta, elimina el registro
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val opciones=resources.getStringArray(R.array.string_array_opciones_dialogo)
        val seleccionPrevia= booleanArrayOf(
            true, //lunes seleccionado
            false, //maartes NO seleccionado
            false   //Miercoles NO seleccionado
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {dialog,
             which,
             isChecked ->
                "Dio clic en el item ${which}"
            }
        )
        val dialogo=builder.create()
        dialogo.show()
    }

    //accion del boton anadirEntrenador
    fun anadirEntrenador(adaptador:ArrayAdapter<BEntrenador>){
        arreglo.add(
            BEntrenador(
                1,
                "Adrian",
                "Descript"
            )
        )
        adaptador.notifyDataSetChanged()
    }
}