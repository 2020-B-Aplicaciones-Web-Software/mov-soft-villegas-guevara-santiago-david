package com.example.firebaseuno

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions


class FMapsActivity : AppCompatActivity() {
    private  lateinit var mapa:GoogleMap
    var permisos=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fmaos)
        solicitarPermisos()

        val botonCarolina=findViewById<Button>(R.id.btn_ir_carolina)
        botonCarolina
            .setOnClickListener{
                val carolina = LatLng(-0.18288452555103193, -78.48449971346241)
                val zoom=17f
                moverCamaraConZoom(carolina,zoom)


            }

        val fragmentoMapa= supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap->
        if(googleMap!=null) {
            mapa = googleMap
            establecerConfiguracionMapa()
            val quicentro = LatLng(-0.176125, -78.480208)
            val titulo = "Quicentro"
            val zoom = 17f
            anadirMarcador(quicentro, titulo)
            moverCamaraConZoom(quicentro, zoom)

            //LINEA
            val poliLineaUno= googleMap
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.1753,-78.4882),
                            LatLng(-0.17158,-78.48483),
                            LatLng(-0.1768,-78.48780)
                        )
                )
            poliLineaUno.tag="linea-1"//ID

            //POLIGONO

            val poligonoUno= googleMap
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.1771,-78.48344),
                            LatLng(-0.1796,-78.4826),
                            LatLng(-0.1771,-78.4814)
                        )
                )
            poligonoUno.fillColor=-0xc771c4
            poligonoUno.tag="poligono-2"//ID
            escuharListeners()
        }

        }
    }
    fun escuharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa","setOnPolygonClickListener ${it}")
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa","setOnPolylineClickListener ${it}")
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa","setOnMarkerClickListener ${it}")
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
    fun anadirMarcador(latLng: LatLng,title:String){
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }
    fun moverCamaraConZoom(latLng: LatLng,zoom:Float=10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng,zoom)
        )
    }
    fun establecerConfiguracionMapa(){
        val contexto= this.applicationContext
        with(mapa){
            val permisosFineLocation= ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos= permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled=true//no tenemos aun permisos
            }
            uiSettings.isZoomControlsEnabled=true
            uiSettings.isMyLocationButtonEnabled=true
        }

    }
    fun solicitarPermisos(){
        val contexto= this.applicationContext
        val permisosFineLocation= ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos= permisosFineLocation==PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos=true
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }

    }
}