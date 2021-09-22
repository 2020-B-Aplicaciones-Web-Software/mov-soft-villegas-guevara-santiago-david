package com.example.firebaseuno

class RestauranteOrdenFirebase(
    val nombre:String,
    val califacionPromedio:Double,
    val uid:String,
) {
    override fun toString(): String {
        return "\nRestaurante: (\nNombre='$nombre' \nCalifación Promedio=$califacionPromedio \nuid='$uid')"
    }
}