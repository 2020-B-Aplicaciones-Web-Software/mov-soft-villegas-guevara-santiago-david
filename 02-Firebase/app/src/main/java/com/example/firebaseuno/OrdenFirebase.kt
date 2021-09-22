package com.example.firebaseuno



class OrdenFirebase(
    val fechaPedido:String,
    val total:Double,
    val calificacion:String,
    val estado:String,
    val restaurante: RestauranteOrdenFirebase,
    val productos: List<ProductoOrdenFirebase>,
    val uid:String,
    val usuario:String,

) {

    override fun toString(): String {
        return "Orden:\nUsuario:'$usuario'\nFecha del Pedido:$fechaPedido' \nValor total:$total \nCalificación:'$calificacion' \nEstado:'$estado'$restaurante \nProductos:$productos"
    }
}