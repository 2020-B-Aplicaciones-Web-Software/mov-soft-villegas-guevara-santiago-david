package com.example.firebaseuno

import com.google.type.Date

class OrdenFirebase(
    val fechaPedido:String,
    val total:Double,
    val calificacion:String,
    val estado:String,
    val restaurante: RestauranteFirebase,
    val productos: ArrayList<ProductoOrdenFirebase>,

) {


}