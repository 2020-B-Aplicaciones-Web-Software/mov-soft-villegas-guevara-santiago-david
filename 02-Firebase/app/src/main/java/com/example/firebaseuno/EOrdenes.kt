package com.example.firebaseuno

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseuno.dto.FirestoreProductoDto
import com.example.firebaseuno.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EOrdenes : AppCompatActivity() {

    var posicionItemSeleccionado=0
    lateinit var  adaptadorRestaurante: ArrayAdapter<RestauranteFirebase>
    lateinit var  adaptadorProducto: ArrayAdapter<ProductoFirebase>
    lateinit var  adaptadorOrden: ArrayAdapter<ProductoOrdenFirebase>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)

        cargarRestaurantes()
        BaseDeDatosMemoria.listaOrdenes.clear()
    }
    fun calcularTotal(): Double {
        val precioTotal=findViewById<TextView>(R.id.tv_precioToral)
        var textoPrecio=""
        var calculoPrecio:Double=0.00
        BaseDeDatosMemoria.listaOrdenes
            .forEach {
                calculoPrecio=calculoPrecio+(it.cantidad*it.precio)
            }
        textoPrecio="Precio total:"+String.format("%.2f", calculoPrecio)
        precioTotal.text=textoPrecio
        return calculoPrecio

    }
    fun cargarInterfaz(){
        adaptadorRestaurante= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            BaseDeDatosMemoria.listaRestauantes// Arreglo

        )
        adaptadorProducto= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            BaseDeDatosMemoria.listaProductos// Arreglo

        )

        adaptadorOrden= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            BaseDeDatosMemoria.listaOrdenes// Arreglo

        )

        val listaOrden=findViewById<ListView>(R.id.lv_lista_productos)
        val spinerProducto = findViewById<Spinner>(R.id.sp_producto)
        val spinerRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        val botonAgregar = findViewById<Button>(R.id.btn_anadir_lista_producto)
        val cantidad = findViewById<EditText>(R.id.et_cantidad_producto)
        val botonPerdir=findViewById<Button>(R.id.btn_completar_pedido)

        listaOrden.adapter=adaptadorOrden
        spinerProducto.adapter=adaptadorProducto
        spinerRestaurante.adapter=adaptadorRestaurante
        registerForContextMenu(listaOrden)

        adaptadorProducto.notifyDataSetChanged()
        adaptadorRestaurante.notifyDataSetChanged()
        adaptadorOrden.notifyDataSetChanged()

        botonAgregar.setOnClickListener{
            if(cantidad.text.toString()!=""&&
                cantidad.text.toString().toInt()>0&&
                spinerRestaurante.getSelectedItemPosition()!=-1&&
                spinerProducto.getSelectedItemPosition()!=-1 ){


                BaseDeDatosMemoria.listaOrdenes.add(
                    ProductoOrdenFirebase(
                        BaseDeDatosMemoria.listaProductos[spinerProducto.getSelectedItemPosition()].nombre,
                        BaseDeDatosMemoria.listaProductos[spinerProducto.getSelectedItemPosition()].precio,
                        cantidad.text.toString().toInt(),
                        BaseDeDatosMemoria.listaProductos[spinerProducto.getSelectedItemPosition()].uid,
                    )
                )

                BaseDeDatosMemoria.listaProductos.removeAt(spinerProducto.getSelectedItemPosition())
                adaptadorProducto.notifyDataSetChanged()
                adaptadorRestaurante.notifyDataSetChanged()
                adaptadorOrden.notifyDataSetChanged()

                cantidad.text.clear()
                calcularTotal()
            }else{
                adaptadorProducto.notifyDataSetChanged()
                adaptadorRestaurante.notifyDataSetChanged()
                adaptadorOrden.notifyDataSetChanged()

            }
        }

        botonPerdir.setOnClickListener{
            if(BaseDeDatosMemoria.listaOrdenes.isNotEmpty()) {

                val restaurante = RestauranteOrdenFirebase(
                    BaseDeDatosMemoria.listaRestauantes[spinerRestaurante.getSelectedItemPosition()].nombre,
                    BaseDeDatosMemoria.listaRestauantes[spinerRestaurante.getSelectedItemPosition()].calificacionPromedio,
                    BaseDeDatosMemoria.listaRestauantes[spinerRestaurante.getSelectedItemPosition()].uid

                )
                val c: Calendar = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd--MM-yyy HH:mm")
                val strDate: String = sdf.format(c.getTime())
                val nuevaOrden = hashMapOf<String, Any>(
                    "fechaPedido" to strDate,
                    "total" to calcularTotal(),
                    "calificacion" to "Sin calificar",
                    "estado" to "Por recibir",
                    "restaurante" to restaurante,
                    "productos" to BaseDeDatosMemoria.listaOrdenes,
                    "usuario" to BAuthUsuario.usuario!!.email,

                )
                val db = Firebase.firestore
                val referencia = db.collection("orden")


                referencia
                    .add(nuevaOrden)
                    .addOnSuccessListener {
                        cargarRestaurantes()
                        cargarProductos()
                        BaseDeDatosMemoria.listaOrdenes.clear()
                        adaptadorProducto.notifyDataSetChanged()
                        adaptadorRestaurante.notifyDataSetChanged()
                        adaptadorOrden.notifyDataSetChanged()
                        val precioTotal=findViewById<TextView>(R.id.tv_precioToral)
                        precioTotal.text="Pedido Realizado"



                    }
                    .addOnFailureListener {}
            }else{
                val precioTotal=findViewById<TextView>(R.id.tv_precioToral)
                precioTotal.text="Agregue productos para realizar el pedido"



            }
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
                cargarProductos()

            }





    }

    fun cargarProductos(){

        var lista= arrayListOf<ProductoFirebase>()
        val db= Firebase.firestore
        val referenciaProducto=db
            .collection("producto")
        referenciaProducto
            .get()
            .addOnSuccessListener { productos ->
                for (producto in productos) {

                    val productoCargado = producto.toObject(FirestoreProductoDto::class.java)

                    lista.add(
                            ProductoFirebase(
                                productoCargado.nombre,
                                productoCargado.precio.toDouble(),
                                producto.id,
                            )
                        )
                }

                BaseDeDatosMemoria.listaProductos=lista
                cargarInterfaz()


            }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater=menuInflater
        inflater.inflate(R.menu.menuordenes,menu)
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        posicionItemSeleccionado=id



    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){

            //ELIMINAR
            R.id.mi_eliminar->{

                BaseDeDatosMemoria.listaProductos.add(ProductoFirebase(
                    BaseDeDatosMemoria.listaOrdenes[posicionItemSeleccionado].nombreProducto,
                    BaseDeDatosMemoria.listaOrdenes[posicionItemSeleccionado].precio,
                    BaseDeDatosMemoria.listaOrdenes[posicionItemSeleccionado].uid)
                )
                BaseDeDatosMemoria.listaOrdenes.removeAt(posicionItemSeleccionado)
                adaptadorProducto.notifyDataSetChanged()
                adaptadorRestaurante.notifyDataSetChanged()
                adaptadorOrden.notifyDataSetChanged()
                val cantidad = findViewById<EditText>(R.id.et_cantidad_producto)
                cantidad.text.clear()
                calcularTotal()


                return true

            }
            else-> super.onContextItemSelected(item)
        }
    }

}




