package com.example.movilescomp2023a

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()

        val boton = findViewById<Button>(R.id.btn_ir_carolina)
        boton.
        setOnClickListener {
            irCarolina()
        }
    }

    fun irCarolina(){
        val carolina = LatLng(-0.1825684318486696,
            -78.48447277600916)
        val zoom = 17f
        moverCamaraConZoom(carolina, zoom)
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val nombrePermiso = android.Manifest.permission.ACCESS_FINE_LOCATION  //Permisos a solicitar
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat.checkSelfPermission(
            contexto,
            nombrePermiso //permiso q se va a checkear
        )

        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED //cte
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, //contexto
                arrayOf(nombrePermiso, nombrePermisoCoarse), //arreglo Permisos
                1 //codigo de petición de los permisos
            )
        }
    }

    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            //with(X) => if (X!= null)
            with(googleMap) {
                establecerConfiguracionMapa()

                moverQuicentro() ///en realidad, a cualquier lado
                anadirPolilinea()
                anadirPoligono()
                escucharListeners()
            }
        }
    }

    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa","setOnPolygonClickListener ${it}")
            it.tag //ID
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa","setOnPolylineClickListener ${it}")
            it.tag //ID
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa","setOnMarkerClickListener ${it}")
            it.tag //ID
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa","setOnCameraMoveListener")
        }

        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa","setOnCameraMoveStartedListener ${it}")
        }

        mapa.setOnCameraIdleListener {
            Log.i("mapa","setOnCameraIdleListener")

        }
    }


    fun anadirPolilinea() {
        with(mapa) {
            val poliLineaUno = mapa.addPolyline(
                PolylineOptions().clickable(true).add(
                    LatLng(-0.17591870406447396, -78.48506472421384),
                    LatLng(-0.17632468492901104, -78.48265589308046),
                    LatLng(-0.17746143138181483, -78.4770533307815)
                )
            )
            poliLineaUno.tag = "linea-1" // <- ID
        }

    }

    fun anadirPoligono(){
        with(mapa){
            //Poligono
            val poligonoUno=mapa.addPolygon(PolygonOptions().clickable(true).add(
                LatLng(-0.1771546902239471,
                    -78.48344981495214),
                LatLng(-0.17968981486125768,
                    -78.48269198043828),
                LatLng(-0.17710958124147777,
                    -78.48142892291516)
            ))
            poligonoUno.fillColor=-0xc771c4
            poligonoUno.tag="poligono-2"
        }
    }
    
    fun moverQuicentro(){
        val zoom=17f
        val quicentro = LatLng(
            -0.17556708490271092, -78.48014901143776
        )
        val titulo="Quicentro"
        val markQuicentro=anadirMarcador(quicentro, titulo)
        markQuicentro.tag=titulo
        moverCamaraConZoom(quicentro,zoom)
    }

    fun anadirMarcador(latLng: LatLng,title:String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(latLng).title(title)
        )!!

    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }
    fun establecerConfiguracionMapa(){
        val contexto=this.applicationContext
        with(mapa){
            val permisosFineLocation=ContextCompat.checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )

            val tienePermisos=permisosFineLocation==PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled=true //hay permisos
                uiSettings.isMyLocationButtonEnabled=true

            }
            uiSettings.isZoomControlsEnabled=true //defecto
        }
    }
}