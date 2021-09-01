package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.firebaseuno.dto.FirestoreProductoDto
import com.example.firebaseuno.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {

    var posicionItemSeleccionado=0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)
        cargarProductos()
        cargarRestaurantes()

        val listaOrden=findViewById<ListView>(R.id.lv_lista_productos)
        val spinerProducto = findViewById<Spinner>(R.id.sp_producto)
        val spinerRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        val botonAgregar = findViewById<Button>(R.id.btn_anadir_lista_producto)
        val cantidad = findViewById<EditText>(R.id.et_cantidad_producto)



        val adaptadorRestaurante= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            BaseDeDatosMemoria.listaRestauantes// Arreglo

        )
        val adaptadorProducto= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            BaseDeDatosMemoria.listaProductos// Arreglo

        )

        val adaptadorOrden= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            BaseDeDatosMemoria.listaOrdenes// Arreglo

        )



        listaOrden.adapter=adaptadorOrden
        spinerProducto.adapter=adaptadorProducto
        spinerRestaurante.adapter=adaptadorRestaurante
        registerForContextMenu(listaOrden)

        adaptadorProducto.notifyDataSetChanged()
        adaptadorRestaurante.notifyDataSetChanged()


        botonAgregar.setOnClickListener{
            if(cantidad.text.toString()!=""&&
                cantidad.text.toString().toInt()>0&&
                spinerRestaurante.getSelectedItemPosition()!=-1&&
                spinerProducto.getSelectedItemPosition()!=-1 ){

                Log.i("s","${spinerProducto.getSelectedItemPosition()}")

                BaseDeDatosMemoria.listaOrdenes.add(
                    OrdenFirebase(BaseDeDatosMemoria.listaProductos[spinerProducto.getSelectedItemPosition()].nombre,
                        BaseDeDatosMemoria.listaProductos[spinerProducto.getSelectedItemPosition()].precio,
                        cantidad.text.toString().toInt(),
                        BaseDeDatosMemoria.listaRestauantes[spinerRestaurante.getSelectedItemPosition()].nombre
                    )
                )

                BaseDeDatosMemoria.listaProductos.removeAt(spinerProducto.getSelectedItemPosition())
                val adaptadorProducto1= ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_spinner_item,//Layout (visual)
                    BaseDeDatosMemoria.listaProductos// Arreglo
                )

                val adaptadorOrden1= ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_list_item_1,//Layout (visual)
                    BaseDeDatosMemoria.listaOrdenes// Arreglo
                )
                listaOrden.adapter=adaptadorOrden1
                spinerProducto.adapter=adaptadorProducto1


            }else{
                adaptadorProducto.notifyDataSetChanged()
                adaptadorRestaurante.notifyDataSetChanged()
                adaptadorOrden.notifyDataSetChanged()

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

                        BaseDeDatosMemoria.listaRestauantes.add(
                            RestauranteFirebase(
                                restauranteCargado.nombre

                            )
                        )

                    }

                }
            }
        BaseDeDatosMemoria.listaRestauantes=lista




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
                                productoCargado.precio.toDouble()
                            )
                        )
                }
            }
        BaseDeDatosMemoria.listaProductos=lista


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
                    BaseDeDatosMemoria.listaOrdenes[posicionItemSeleccionado].precio)
                )
                BaseDeDatosMemoria.listaOrdenes.removeAt(posicionItemSeleccionado)
                val listaOrden=findViewById<ListView>(R.id.lv_lista_productos)
                val spinerProducto = findViewById<Spinner>(R.id.sp_producto)
                val adaptadorProducto= ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_spinner_item,//Layout (visual)
                    BaseDeDatosMemoria.listaProductos// Arreglo
                )

                val adaptadorOrden= ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_list_item_1,//Layout (visual)
                    BaseDeDatosMemoria.listaOrdenes// Arreglo
                )
                listaOrden.adapter=adaptadorOrden
                spinerProducto.adapter=adaptadorProducto


                return true

            }




            else-> super.onContextItemSelected(item)
        }
    }

}




