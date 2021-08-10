package com.example.deber02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imgLogo=findViewById<ImageView>(
            R.id.image_logo
        )
        val imgOpciones=findViewById<ImageView>(
            R.id.image_opciones
        )

        val recyclerViewJuegos=findViewById<RecyclerView>(
            R.id.recyclerJuego
        )
        val juegos=Videojuego.arregloVideojuego
        iniciarRecyclerView(
            juegos,
            this,
            recyclerViewJuegos
        )


        imgLogo.setOnClickListener{
            abrirActividad(
               MainActivity::class.java
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


}