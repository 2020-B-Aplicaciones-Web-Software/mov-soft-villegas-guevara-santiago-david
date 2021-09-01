package com.example.firebaseuno.dto

data class FirestoreUsuarioDto (
    val uid:String="",
    val email:String="",
    var roles: ArrayList<String> = arrayListOf()

        ){

}