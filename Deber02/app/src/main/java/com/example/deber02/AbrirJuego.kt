package com.example.deber02

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AbrirJuego : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abrir_juego)
        val juego=intent.getParcelableExtra<Videojuego>("juego")
        menuDesplegable()

        val imgPortadaInicio=findViewById<ImageView>(
            R.id.img_portadaInicio
        )
        if (juego != null) {
            imgPortadaInicio.setImageResource(juego.informacion)
        }

        val imgLogo=findViewById<ImageView>(
            R.id.image_logoJ
        )
        val imgOpciones=findViewById<ImageView>(
            R.id.image_opcionesJ
        )
        val imgPlay=findViewById<ImageView>(
            R.id.imgPlayJ
        )
        val imgPc=findViewById<ImageView>(
            R.id.imgPcJ
        )
        val imgNintendo=findViewById<ImageView>(
            R.id.imgNintendoJ
        )
        val imgXbox=findViewById<ImageView>(
            R.id.imgXboxJ
        )
        val nombreJuego=findViewById<TextView>(
            R.id.text_nombreJuego
        )
        if (juego != null) {
            nombreJuego.text=juego.nombre
        }

        val recyclerViewJuegos=findViewById<RecyclerView>(
            R.id.recyclerJuegoJ
        )

        val buscar=findViewById<FloatingActionButton>(
            R.id.boton_buscarJ
        )
        val buscarText=findViewById<EditText>(
            R.id.editText_buscarJ
        )

        val juegos=Videojuego.arregloVideojuego
        iniciarRecyclerView(
            juegos,
            this,
            recyclerViewJuegos
        )
        val imgPlataforma=findViewById<ImageView>(
            R.id.img_plataformaJ
        )
        if (juego != null) {
            imgPlataforma.setImageResource(juego.plataforma)
        }


        val plat=findViewById<TextView>(
            R.id.text_plataforma
        )
        val precioDescuento=findViewById<TextView>(
            R.id.text_precioConDescuento
        )
        val calculoPrecio= juego!!.precio*(100.00-juego.descuento)/100.00
        val precioC="$"+String.format("%.2f", calculoPrecio);
        precioDescuento.text=precioC

        val descuento=findViewById<TextView>(
            R.id.text_descuento
        )
        val descuentoC="-"+String.format("%.2f", juego.descuento)+"%";
        descuento.text=descuentoC

        val precio=findViewById<TextView>(
            R.id.text_precio
        )
        val preciosinDescuento="  $"+ juego?.let { String.format("%.2f", it.precio) };
        precio.text= preciosinDescuento

        if (juego != null) {
            plat.text=juego.confMinima
        }
        val fecha=findViewById<TextView>(
            R.id.text_desarrollador
        )
        if (juego != null) {
            fecha.text=juego.fechaLanzamiento
        }
        buscar.setOnClickListener{

            val juegosFilter:List<Videojuego> =Videojuego.arregloVideojuego
                .filter { valorActual: Videojuego->
                    val nombreJuego:String= valorActual.nombre!!
                    val buscar: Boolean= nombreJuego.contains(buscarText.text)
                    return@filter buscar

                }
            iniciarRecyclerView(
                juegosFilter,
                this,
                recyclerViewJuegos
            )
        }


        imgLogo.setOnClickListener{
            abrirActividad(
                MainActivity::class.java
            )
        }
        imgPc.setOnClickListener{
            val juegosFilter:List<Videojuego> =Videojuego.arregloVideojuego
                .filter { valorActual: Videojuego->
                    val pc: Boolean=valorActual.plataforma==R.drawable.ic_origin
                    return@filter pc

                }
            iniciarRecyclerView(
                juegosFilter,
                this,
                recyclerViewJuegos
            )
        }
        imgPlay.setOnClickListener{
            val juegosFilter:List<Videojuego> =Videojuego.arregloVideojuego
                .filter { valorActual: Videojuego->
                    val play: Boolean=valorActual.plataforma==R.drawable.ic_play
                    return@filter play

                }
            iniciarRecyclerView(
                juegosFilter,
                this,
                recyclerViewJuegos
            )
        }
        imgXbox.setOnClickListener{
            val juegosFilter:List<Videojuego> =Videojuego.arregloVideojuego
                .filter { valorActual: Videojuego->
                    val xbox: Boolean=valorActual.plataforma==R.drawable.ic_xboxlog
                    return@filter xbox

                }
            iniciarRecyclerView(
                juegosFilter,
                this,
                recyclerViewJuegos
            )
        }
        imgNintendo.setOnClickListener{
            val juegosFilter:List<Videojuego> =Videojuego.arregloVideojuego
                .filter { valorActual: Videojuego->
                    val nintendo: Boolean=valorActual.plataforma==R.drawable.ic_switch
                    return@filter nintendo

                }
            iniciarRecyclerView(
                juegosFilter,
                this,
                recyclerViewJuegos
            )
        }
    }
    fun menuDesplegable(){
        val imgOpciones=findViewById<ImageView>(
            R.id.image_opcionesJ
        )
        val wrapper: Context = ContextThemeWrapper(this, R.style.miEstilo)

        val menu= PopupMenu(wrapper,
            imgOpciones)
        menu.inflate(R.menu.opciones)

        imgOpciones.setOnClickListener{
            val popup= PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible=true
            val menuP=popup.get(menu)
            menuP.javaClass
                .getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menuP,true)
            menu.show()


        }

    }
    fun iniciarRecyclerView(
        lista: List<Videojuego>,
        actividad: AbrirJuego,
        recyclerView: RecyclerView
    ) {
        val adaptador=RecyclerViewAdaptadorJuegoAbrr(
            actividad,
            lista,
            recyclerView
        )
        recyclerView.adapter=adaptador
        recyclerView.itemAnimator=androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager=androidx.recyclerview.widget.LinearLayoutManager(actividad, recyclerView.horizontalFadingEdgeLength,false)
        adaptador.notifyDataSetChanged()
    }
    fun abrirActividad(
        clase: Class<*>
    ){

        val intentExplicito= Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }

    fun abrirJuego(
        clase: Class<*>,
        juego:Videojuego
    ) {
        val intentExplicito = Intent(this,clase)

        intentExplicito.putExtra("juego",juego)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

}