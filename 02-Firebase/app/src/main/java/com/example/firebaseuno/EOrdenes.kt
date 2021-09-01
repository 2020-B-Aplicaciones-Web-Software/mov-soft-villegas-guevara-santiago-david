package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.firebaseuno.dto.FirestoreProductoDto
import com.example.firebaseuno.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {
    var listaProductos= arrayListOf<ProductoFirebase>()
    var listaRestauantes= arrayListOf<RestauranteFirebase>()






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)
        listaProductos=cargarProductos()
        listaRestauantes=cargarRestaurantes()




        val spinerProducto = findViewById<Spinner>(R.id.sp_producto)
        val spinerRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        val botonAgregar = findViewById<Button>(R.id.btn_anadir_lista_producto)
        val cantidad = findViewById<EditText>(R.id.et_cantidad_producto)
        Log.i("i","${listaProductos.size} de ${listaRestauantes.size}")
        val adaptadorProducto= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            listaProductos// Arreglo

        )
        val adaptadorRestaurante= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_spinner_item,//Layout (visual)
            listaRestauantes// Arreglo

        )
        Log.i("i","${listaProductos.size} de ${listaRestauantes.size}")

        spinerProducto.adapter=adaptadorProducto
        spinerRestaurante.adapter=adaptadorRestaurante

        botonAgregar.setOnClickListener{
            if(cantidad.text.toString()!=""){
                Log.i("i","${listaProductos.size} de ${listaRestauantes.size}")
                cantidad.text.clear()
                Log.i("i","Restaurante:${spinerRestaurante.getSelectedItemPosition()} Producto:${spinerProducto.getSelectedItemPosition()} Cantidad:${cantidad.text.toString()}")
            }


        }







    }
    fun cargarRestaurantes():ArrayList<RestauranteFirebase>{
        val db= Firebase.firestore
        var listaRestauantes= arrayListOf<RestauranteFirebase>()

        val referenciaRestaurante=db
            .collection("restaurante")
        referenciaRestaurante
            .get()
            .addOnSuccessListener { restaurantes ->
                for (restaurante in restaurantes) {

                    val restauranteCargado = restaurante.toObject(FirestoreRestauranteDto::class.java)


                    if (restauranteCargado != null) {

                        listaRestauantes.add(
                            RestauranteFirebase(
                                restauranteCargado.nombre

                            )
                        )

                    }

                }
            }
        return  listaRestauantes



    }

    fun cargarProductos():ArrayList<ProductoFirebase>{
        var listaProductos= arrayListOf<ProductoFirebase>()

        val db= Firebase.firestore
        val referenciaProducto=db
            .collection("producto")
        referenciaProducto
            .get()
            .addOnSuccessListener { productos ->
                for (producto in productos) {

                    val productoCargado = producto.toObject(FirestoreProductoDto::class.java)


                    if (productoCargado != null) {

                        listaProductos.add(
                            ProductoFirebase(
                                productoCargado.nombre,
                                productoCargado.precio.toDouble()
                            )
                        )

                    }

                }
            }
        return listaProductos

    }

}




