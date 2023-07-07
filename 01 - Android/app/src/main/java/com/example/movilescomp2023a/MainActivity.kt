package com.example.movilescomp2023a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {  //viene de AppCompatActivity

    //variable para establecer Intent Explicito
    val callbackContenidoIntentExplicito=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if (result.resultCode==Activity.RESULT_OK){
                if (result.data != null){
                    //Logica Negocio
                    val data =result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }

    //para devolver un telefono
    val callbackIntentPickUri=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode=== RESULT_OK){
                if(result.data!=null){
                    if (result.data!!.data != null){
                        var uri: Uri =result.data!!.data!!
                        val cursor=contentResolver.query(uri,null,null,null,null, null)
                        cursor?.moveToFirst()

                        val indiceTelefono=cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )

                        val telefono=cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        "Telefono ${telefono}"
                    }
                }
            }
        }

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

        //boton para el seleccionador de contactos
        val botonIntentImplicito=findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener{
            val intentConRespuesta=Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }

        //otro boton
        val botonIntentExlicito=findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExlicito.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }
    }

    fun abrirActividadConParametros(
        clase: Class <*>
    ){
        val intentExplicito=Intent(this,clase)

        //enviar paramteros (variables primitivas)
        intentExplicito.putExtra("nombre", "adrian")
        intentExplicito.putExtra("apellido","eguez")
        intentExplicito.putExtra("edad",34)

        callbackContenidoIntentExplicito.launch(intentExplicito)
    }



    fun irActividad( //metodo de la  clase
        clase:Class<*>
    ){
        val intent =Intent(this,clase)
        startActivity(intent) //intent explicito
    }

}