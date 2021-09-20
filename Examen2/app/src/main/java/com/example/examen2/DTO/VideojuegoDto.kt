package com.example.examen2.DTO

class VideojuegoDto(
    var id:String="",
    var nombre:String="",
    var recaudacion:Double=0.0,
    var fechaSalida: String="",
    var generoPrincipal: String="",
    var multijugador: Boolean=false,
    var longitud: Double?=0.0,
    var latitud:Double?=0.0,
) {
}