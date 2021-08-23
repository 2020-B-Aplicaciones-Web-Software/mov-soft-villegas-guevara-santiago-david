package com.example.deber02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imgLogo=findViewById<ImageView>(
            R.id.image_logo
        )
        val imgOpciones=findViewById<ImageView>(
            R.id.image_opciones
        )
        val imgPlay=findViewById<ImageView>(
            R.id.imgPlay
        )
        val imgPc=findViewById<ImageView>(
            R.id.imgPc
        )
        val imgNintendo=findViewById<ImageView>(
            R.id.imgNintendo
        )
        val imgXbox=findViewById<ImageView>(
            R.id.imgXbox
        )

        val recyclerViewJuegos=findViewById<RecyclerView>(
            R.id.recyclerJuego
        )
        val buscar=findViewById<FloatingActionButton>(
            R.id.boton_buscar
        )
        val buscarText=findViewById<EditText>(
            R.id.editText_buscar
        )

        val juegos=Videojuego.arregloVideojuego
        iniciarRecyclerView(
            juegos,
            this,
            recyclerViewJuegos
        )
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
        menuDesplegable()

    }

    fun iniciarRecyclerView(
        lista: List<Videojuego>,
        actividad: MainActivity,
        recyclerView: RecyclerView
    ) {
        val adaptador=RecyclerViewAdaptadorJuego(
            actividad,
            lista,
            recyclerView
        )
        recyclerView.adapter=adaptador
        recyclerView.itemAnimator=androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager=androidx.recyclerview.widget.GridLayoutManager(actividad,3)
        adaptador.notifyDataSetChanged()
    }

    fun menuDesplegable(){
        val imgOpciones=findViewById<ImageView>(
            R.id.image_opciones
        )
        val wrapper: Context = ContextThemeWrapper(this, R.style.miEstilo)

        val menu=PopupMenu(wrapper,
            imgOpciones)
        menu.inflate(R.menu.opciones)

        imgOpciones.setOnClickListener{
            val popup=PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible=true
            val menuP=popup.get(menu)
            menuP.javaClass
                .getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menuP,true)
            menu.show()
        }

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