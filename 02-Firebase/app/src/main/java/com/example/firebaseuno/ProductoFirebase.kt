package com.example.firebaseuno

import com.example.firebaseuno.dto.FirestoreProductoDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductoFirebase(
    val nombre:String,
    val precio: Double,
    val uid:String,

) {
    override fun toString(): String {
        return "${nombre} "
    }


}