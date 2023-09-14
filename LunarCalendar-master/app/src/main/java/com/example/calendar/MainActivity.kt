package com.example.calendar

import android.content.Intent
import java.text.SimpleDateFormat
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.CalendarView.OnDateChangeListener
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    private var idItemSeleccionado = 0
    val ets:ArrayList<String> = arrayListOf()

    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    lateinit var moonShape : TextView
    lateinit var nextPage : Button
    lateinit var saveEvent : Button
    lateinit var glosarioButton : Button
    lateinit var events : ListView
    lateinit var eventName : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView=findViewById<ListView>(R.id.LunaLlena)
        val adaptador=ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //como se va a ver (XML)
            ets
        )

        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()

        // initializing variables of
        // list view with their ids.
        var sendDate = "11/8/1999"
        dateTV = findViewById(R.id.idTVDate)
        calendarView = findViewById(R.id.calendarView)
        moonShape = findViewById(R.id.phaseView)
        nextPage = findViewById(R.id.moonDisplay)
        saveEvent = findViewById(R.id.saveEvent)
        events= findViewById(R.id.LunaLlena)
        eventName = findViewById(R.id.eventName)
        glosarioButton = findViewById(R.id.glosario)

        val monthMap = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        val moonPhases = arrayOf("Luna Nueva", "Creciente Creciente", "Primer Cuarto Creciente", "Gibbosa Creciente", "Luna Llena", "Gibbosa Menguante", "Último Cuarto Creciente", "Creciente Menguante")

        // on below line we are adding set on
        // date change listener for calendar view.
        val calen=Calendar.getInstance()

        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                   val Date = (dayOfMonth.toString() + "-"
                            + monthMap[month] + "-" + year)
                    sendDate = Date
                    val sendDate = (dayOfMonth.toString() + "/"
                            + (month+1) + "/" + year)
                    numOfDays(sendDate, moonPhases)
                    dateTV.setText(Date)

                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    // abrir el diálogo pasando la fecha seleccionada.
                    mostrarDialogoFechaSeleccionada(selectedDate.time)

                    //registerForContextMenu(listView)
                    cargarDatosDesdeFirestore(adaptador,Date)
                    adaptador.notifyDataSetChanged() // Actualiza la ListView
    })

        nextPage.setOnClickListener {
            intent = Intent(this, moonDisplay::class.java)
            val bundle = Bundle()
            bundle.putString("date", sendDate)
            bundle.putString("phaseName", moonShape.text.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }

        saveEvent.setOnClickListener {
           val txt = eventName.text.toString()
           val dt = dateTV.text.toString()

            val db= Firebase.firestore
            val id = Date().time
            val evs = db.collection("eventos")

            val evento = hashMapOf(
                "evento" to txt,
                "fecha" to dt
            )

            evs.document(id.toString()).set(evento)
                .addOnSuccessListener {
                    cargarDatosDesdeFirestore(adaptador,dt)
                    eventName.text = ""
                }
                .addOnFailureListener { }
            //evs.document(id.toString()).update("id",id.toString())
        }

        glosarioButton.setOnClickListener {
            // Aquí puedes agregar el código para manejar el clic del botón "Glosario".
            // Puedes abrir una nueva actividad o realizar cualquier otra acción necesaria.
            // Por ejemplo:
            val intent = Intent(this, GlosarioActivity::class.java)
            startActivity(intent)
        }

    }

    fun numOfDays(sendDate: String, moonPhases: Array<String>){
        val baseDate = "11/8/1999"
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val startDate = sdf.parse(baseDate)
        val endDate = sdf.parse(sendDate)
        val timeGone = kotlin.math.abs(endDate.time - startDate.time)
        val daysGone = timeGone / (24 * 60 * 60 * 1000)
        val moonAge = daysGone % 29.53058770576
        moonShape.text = moonAge.toString()
        if((0 < moonAge && moonAge <= 1) || (28.530588 < moonAge && moonAge <= 29.530588))
            moonShape.text = moonPhases[0]
        else if(1 < moonAge && moonAge <= 6.382647)
            moonShape.text = moonPhases[1]
        else if(6.382647 < moonAge && moonAge <= 8.382647)
            moonShape.text = moonPhases[2]
        else if(8.382647  < moonAge && moonAge <= 13.765294)
            moonShape.text = moonPhases[3]
        else if(13.765294 < moonAge && moonAge <= 15.765294)
            moonShape.text = moonPhases[4]
        else if(15.765294 < moonAge && moonAge <= 21.147941)
            moonShape.text = moonPhases[5]
        else if(21.147941 < moonAge && moonAge <= 23.147941)
            moonShape.text = moonPhases[6]
        else if(23.147941 < moonAge && moonAge <= 28.530588)
            moonShape.text = moonPhases[7]
    }

    fun cargarDatosDesdeFirestore(adaptador: ArrayAdapter<String>, date:String){
        val db=Firebase.firestore
        val cRefUnico=db.collection("eventos")
        ets.clear() //cada consulta se limpia el arreglo
        adaptador.notifyDataSetChanged()
        cRefUnico
            .whereEqualTo("fecha", date)
            .get()
            .addOnSuccessListener { //it
                for(cont in it){
                    cont.id //id quemado o autogenerado
                    anadirAArregloCont(cont)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener{
                //Errores
            }
    }

    fun anadirAArregloCont(cont: QueryDocumentSnapshot){
        ets.add( cont.data.get("evento") as String  )
    }

    private fun mostrarDialogoFechaSeleccionada(selectedDate: Date) {
    var consejo=""

    if(moonShape.text == "Luna Nueva")
        consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Preparar el suelo y realizar tareas de limpieza en el jardín." +
                " También es un buen momento para sembrar cultivos de raíces y tubérculos," +
                " como papas y camotes.\n" +
                "\n" +
                "Qué no hacer \uD83D\uDEAB: Evita la poda intensiva o la cosecha de cultivos que produzcan" +
                " frutos sobre el suelo, ya que esta fase no es propicia para ese tipo de " +
                "actividades."
        else if(moonShape.text == "Creciente Creciente")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Continúa sembrando cultivos que crecen por encima del suelo," +
                    " como maíz, frijoles y algunas variedades de flores.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la poda excesiva, ya que las plantas pueden necesitar " +
                    "su follaje para protegerse del sol."
        else if(moonShape.text == "Primer Cuarto Creciente")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Realizar trasplantes de plántulas y plantas perennes.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la poda o el corte drástico de las plantas en esta fase," +
                    " ya que pueden tardar más en recuperarse debido al clima cálido."
        else if(moonShape.text == "Gibbosa Creciente")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Continúa sembrando cultivos de crecimiento rápido y flores " +
                    "ornamentales. También es un buen momento para aplicar fertilizantes por las" +
                    " condiciones favorables para el crecimiento.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la poda intensiva, ya que las plantas pueden ser más " +
                    "susceptibles a enfermedades en condiciones húmedas."
        else if(moonShape.text == "Luna Llena")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Cosecha de cultivos y  poda. Se cree que la energía de la planta" +
                    " se concentra en las raíces, por lo que la cosecha de cultivos subterráneos " +
                    "puede ser beneficiosa.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la siembra y la trasplantación durante esta fase, ya que" +
                    " las plantas pueden ser más susceptibles a enfermedades en condiciones húmedas."
        else if(moonShape.text == "Gibbosa Menguante")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Continúa con la cosecha y la poda, especialmente de cultivos que " +
                    "producen frutos. También es un buen momento para controlar plagas, ya que el" +
                    " clima puede favorecer su aparición.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la siembra y la fertilización excesiva, ya que el clima" +
                    " húmedo puede afectar la absorción de nutrientes."
        else if(moonShape.text == "Último Cuarto Creciente")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Realizar labores de mantenimiento en el jardín y preparar el suelo " +
                    "para futuras siembras. El clima suele ser estable en esta etapa.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la siembra y la poda importante, ya que se cree que las " +
                    "plantas pueden necesitar más energía para recuperarse."
        else if(moonShape.text == "Creciente Menguante")
            consejo="Qué hacer \uD83D\uDC69\u200D\uD83C\uDF3E: Aprovecha esta fase para la poda y el control de plagas. El clima " +
                    "puede ser propicio para estas actividades.\n" +
                    "\n" +
                    "Qué no hacer \uD83D\uDEAB: Evita la siembra y la fertilización excesiva durante esta fase," +
                    " ya que las plantas pueden necesitar menos nutrientes en este momento."

        //  texto del diálogo.
    val tit = moonShape.text.toString() + ": ${SimpleDateFormat("dd/MM/yyyy").format(selectedDate)}"
    val texto = consejo

        // Crea y muestra el diálogo.
        val builder = AlertDialog.Builder(this)
        builder.setTitle(tit)
            .setMessage(texto)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }

        val dialogo = builder.create()
        dialogo.show()
    }

    /*
   //Para Eliminar
   override fun onCreateContextMenu(
       menu: ContextMenu?,
       v: View?,
       menuInfo: ContextMenu.ContextMenuInfo?
   ) {
       super.onCreateContextMenu(menu, v, menuInfo)
       val inflater=menuInflater
       inflater.inflate(R.menu.menuc,menu)

       val info=menuInfo as AdapterView.AdapterContextMenuInfo
       val id=info.position
       idItemSeleccionado=id
   }

   //del item seleccionado
   override fun onContextItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {

           R.id.mi_eliminar->{
              // val idE = ets[idItemSeleccionado]
               ets.removeAt(idItemSeleccionado)

              // eliminarRegistro(idE)
               true
           }
           else-> super.onContextItemSelected(item)
       }
   }


   fun eliminarRegistro(ets:String){  //es solo el evento

       /*   val db=Firebase.firestore
          val ref=db.collection("paises")
          ref .document(id)  //como psar el id del documento?
              .delete()
              .addOnCompleteListener{task ->
                  if (task.isSuccessful) {
                      ets.removeAt(idItemSeleccionado)
                  } else {
                  }
              }
              .addOnFailureListener {
              }

        */
   }

    */

}
