package com.example.firebaseuno

class OrdenFirebase(
    val nombreProducto:String,
    val precio: Double,
    val cantidad:Int,
    val nombreRestaurante:String
) {

    override fun toString(): String {
        return "Nombre Restaurante: $nombreRestaurante\nNombre Producto: $nombreProducto\nPrecio" +
                " Unitario: $precio\nCantidad:$cantidad\nPrecio Total: ${cantidad*precio} "
    }
}