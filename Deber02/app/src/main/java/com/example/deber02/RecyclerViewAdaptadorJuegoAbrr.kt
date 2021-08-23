package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdaptadorJuegoAbrr (
    private val contexto: AbrirJuego,
    private val listaJuego: List<Videojuego>,
    private val recyclerView: RecyclerView,
): RecyclerView.Adapter<RecyclerViewAdaptadorJuegoAbrr.MyViewHolder>() {
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
                contexto.abrirActividad(AbrirJuego::class.java)

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
        val precio="$"+String.format("%.2f", calculoPrecio);
        val descuento="-"+String.format("%.0f", juego.descuento)+"%"
        holder.descuento.text=descuento
        holder.precio.text=precio
        holder.portada.setImageResource(juego.portada)
        holder.plataforma.setImageResource(juego.plataforma)
        holder.portada.setOnClickListener(View.OnClickListener
        { contexto.
        abrirJuego(AbrirJuego::class.java,juego) })

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


