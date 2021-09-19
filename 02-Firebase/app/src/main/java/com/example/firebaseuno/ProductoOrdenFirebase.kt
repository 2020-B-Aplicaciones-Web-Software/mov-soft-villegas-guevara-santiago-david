package com.example.firebaseuno

class ProductoOrdenFirebase(
    val nombreProducto:String,
    val precio: Double,
    val cantidad:Int,

    val uid:String,
) {

    override fun toString(): String {
        return "Nombre Producto: $nombreProducto\nPrecio" +
                " Unitario: $precio\nCantidad:$cantidad\nPrecio Total: ${cantidad*precio} "
    }
}