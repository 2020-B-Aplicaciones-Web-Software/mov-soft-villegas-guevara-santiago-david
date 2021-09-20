package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

import com.example.firebaseuno.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerOrdenesRestaurante : AppCompatActivity() {
    var posicionItemSeleccionado=0
    lateinit var  adaptadorRestaurante: ArrayAdapter<RestauranteFirebase>
    lateinit var  adaptadorFiltro: ArrayAdapter<String>
    lateinit var  adaptadorOrden: ArrayAdapter<OrdenFirebase>
    val filtros: List<String> = listOf("Por recibir","Preparando","Enviado","Entregado","Cancelado")
    var posFiltro:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ordenes_restaurante)
        BaseDeDatosMemoria.listaPedido.clear()
        cargarRestaurantes()



    }
    fun cargarInterfaz(){
        adaptadorRestaurante= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            BaseDeDatosMemoria.listaRestauantes// Arreglo

        )
        adaptadorFiltro= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            filtros// Arreglo

        )

        adaptadorOrden= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            BaseDeDatosMemoria.listaPedido// Arreglo

        )

        val spinerFiltro = findViewById<Spinner>(R.id.sp_filtro)
        val spinerRestaurante = findViewById<Spinner>(R.id.sp_restaurantesView)
        val botonFiltrar=findViewById<Button>(R.id.btn_frilto)
        val listaOrden=findViewById<ListView>(R.id.lv_lista_productosRestaurante)
        spinerFiltro.adapter=adaptadorFiltro
        spinerRestaurante.adapter=adaptadorRestaurante
        listaOrden.adapter=adaptadorOrden
        if (filtros[spinerFiltro.getSelectedItemPosition()]=="Por recibir" ||
            filtros[spinerFiltro.getSelectedItemPosition()]=="Preparando" ){
            registerForContextMenu(listaOrden)
        }
        spinerFiltro.setSelection(posFiltro)
        botonFiltrar.setOnClickListener {
            posFiltro=spinerFiltro.getSelectedItemPosition()
            cargarPedidos(filtros[spinerFiltro.getSelectedItemPosition()],
                BaseDeDatosMemoria.listaRestauantes[spinerRestaurante.getSelectedItemPosition()].uid)

        }




    }

    fun cargarRestaurantes(){
        var lista= arrayListOf<RestauranteFirebase>()
        val db= Firebase.firestore


        val referenciaRestaurante=db
            .collection("restaurante")
        referenciaRestaurante
            .get()
            .addOnSuccessListener { restaurantes ->
                for (restaurante in restaurantes) {
                    val restauranteCargado = restaurante.toObject(FirestoreRestauranteDto::class.java)

                    if (restauranteCargado != null) {

                        lista.add(
                            RestauranteFirebase(
                                restauranteCargado.nombre,
                                restauranteCargado.calificacionPromedio,
                                restauranteCargado.sumatoriaCalificaciones,
                                restauranteCargado.usuariosCalicado,
                                restaurante.id,
                                )
                        )

                    }

                }
                BaseDeDatosMemoria.listaRestauantes=lista
                cargarInterfaz()

            }
    }
    fun cargarPedidos(estado:String,resutaurante:String){
        var lista= arrayListOf<OrdenFirebase>()
        val db = Firebase.firestore
        val refOrdenes = db.collection("orden")
        refOrdenes
            .whereEqualTo("estado",estado )
            .whereEqualTo("restaurante.uid",resutaurante )
            .orderBy("fechaPedido", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {ordenes->

                ordenes.forEach{
                    val productos = it.data["productos"] as ArrayList< HashMap<String, Any> >

                    val listaProductos = productos.map {
                        ProductoOrdenFirebase(
                            it["nombreProducto"].toString(),
                            it["precio"].toString().toDouble(),
                            it["cantidad"].toString().toInt(),
                            it["uid"].toString()
                        )
                    }
                    lista.add(
                        OrdenFirebase(it["fechaPedido"].toString(),
                            it["total"].toString().toDouble(),
                            it["calificacion"].toString(),
                            it["estado"].toString(),
                            RestauranteOrdenFirebase(it["restaurante.nombre"].toString(),
                                it["restaurante.califacionPromedio"].toString().toDouble(),
                                it["restaurante.uid"].toString()),
                            listaProductos,
                            it.id,
                            it["usuario"].toString()

                            )
                    )


                }

                BaseDeDatosMemoria.listaPedido=lista
                cargarInterfaz()


            }
            .addOnFailureListener {  }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater=menuInflater
        val spinerFiltro = findViewById<Spinner>(R.id.sp_filtro)
        if (filtros[spinerFiltro.getSelectedItemPosition()]=="Por recibir" ) {
            inflater.inflate(R.menu.menuporrecibir, menu)
        }
        else if (filtros[spinerFiltro.getSelectedItemPosition()]=="Preparando" ){
            inflater.inflate(R.menu.menupreparando, menu)

        }
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        posicionItemSeleccionado=id



    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){

            //preparando
            R.id.mi_preparando->{

                cambiarEstado("Preparando",
                    BaseDeDatosMemoria.listaPedido[posicionItemSeleccionado].uid)
                BaseDeDatosMemoria.listaPedido.removeAt(posicionItemSeleccionado)
                adaptadorOrden.notifyDataSetChanged()

                return true

            }
            //cancelado
            R.id.mi_cancelado-> {

                cambiarEstado("Cancelado",
                    BaseDeDatosMemoria.listaPedido[posicionItemSeleccionado].uid)
                BaseDeDatosMemoria.listaPedido.removeAt(posicionItemSeleccionado)
                adaptadorOrden.notifyDataSetChanged()

                return true

            }
            //enviado
            R.id.mi_enviado-> {

                cambiarEstado("Enviado",
                    BaseDeDatosMemoria.listaPedido[posicionItemSeleccionado].uid)
                BaseDeDatosMemoria.listaPedido.removeAt(posicionItemSeleccionado)
                adaptadorOrden.notifyDataSetChanged()

                return true

            }
            else-> super.onContextItemSelected(item)
        }
    }
    fun cambiarEstado(estado: String,documento:String){

        val db = Firebase.firestore
        val referenciaOrden=db.collection("orden").document(documento)
        db.runTransaction { transaction ->
            transaction.update(referenciaOrden, "estado", estado)

        }


    }

}