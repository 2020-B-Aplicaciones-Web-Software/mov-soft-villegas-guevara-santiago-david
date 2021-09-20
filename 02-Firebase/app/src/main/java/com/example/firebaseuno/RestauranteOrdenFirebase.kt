package com.example.firebaseuno

class RestauranteOrdenFirebase(
    val nombre:String,
    val califacionPromedio:Double,
    val uid:String,
) {
    override fun toString(): String {
        return "\n Restaurante: (\nNombre='$nombre', \nCalifación Promedio=$califacionPromedio, \n uid='$uid')"
    }
}