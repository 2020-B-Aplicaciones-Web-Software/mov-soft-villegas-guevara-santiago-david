package com.example.deber02

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdaptadorJuego(
    private val contexto: MainActivity,
    private val listaJuego: List<Videojuego>,
    private val recyclerView: RecyclerView,
): RecyclerView.Adapter<RecyclerViewAdaptadorJuego.MyViewHolder>() {
    inner  class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val descuento: TextView
        val precio: TextView
        val portada: ImageView
        val plataforma: ImageView


        init {

            descuento = view.findViewById(R.id.textView_descuento)
            precio = view.findViewById(R.id.textView_precio)
            portada = view.findViewById(R.id.img_portada)
            plataforma = view.findViewById(R.id.img_plataforma)
            portada.setOnClickListener {
                contexto.abrirActividad(MainActivity::class.java)

            }

        }




    }
    //tamanio del arreglo
    override fun getItemCount(): Int {
        return listaJuego.size

    }
    //Setea los datos de cada iteracion del arreglo
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val juego=listaJuego[position]
        val calculoPrecio=juego.precio*(100.00-juego.descuento)/100.00
        val precio="$"+calculoPrecio.toString()
        val descuento="-"+juego.descuento.toString()
        holder.descuento.text=descuento
        holder.precio.text=precio
        holder.portada.setImageResource(juego.portada)
        holder.plataforma.setImageResource(juego.plataforma)

        if (juego.descuento==0.00){
            holder.descuento.isVisible=false

        }

    }
    //definimos el layout a usar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  itemView= LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recyclerjuego,
                parent,
                false
            )
        return  MyViewHolder(itemView)
    }




}

