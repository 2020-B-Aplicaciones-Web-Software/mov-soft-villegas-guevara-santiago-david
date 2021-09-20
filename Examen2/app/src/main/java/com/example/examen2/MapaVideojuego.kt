package com.example.examen2

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapaVideojuego : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_videojuego)
        solicitarPermisos()

        val empresa = intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")
        val videojuego = intent.getParcelableExtra<Videojuego>("Juego")
        if (videojuego != null) {
            this.setTitle(videojuego.nombre)
        } else {
            this.setTitle("")
        }

        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            if (googleMap != null) {
                mapa = googleMap
                establecerConfiguracionMapa()
                val juegoUbicacion = LatLng(videojuego!!.latitud!!, videojuego!!.longitud!!)
                val titulo = videojuego.nombre
                val zoom = 17f
                anadirMarcador(juegoUbicacion, titulo!!)
                moverCamaraConZoom(juegoUbicacion, zoom)


            }

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