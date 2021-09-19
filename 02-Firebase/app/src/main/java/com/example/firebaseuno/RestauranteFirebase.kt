package com.example.firebaseuno

class RestauranteFirebase(
    val nombre:String,
    val calificacionPromedio:Double,
    val sumatoriaCalificaciones:Int,
    val usuariosCalicado:Int,
    val uid:String,

) {
    override fun toString(): String {
        return "${nombre}"
    }

}