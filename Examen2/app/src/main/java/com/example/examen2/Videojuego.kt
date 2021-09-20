package com.example.examen2

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class Videojuego (
    var id:String?,
    var nombre:String?,
    var recaudacion:Double?,
    var fechaSalida: String?,
    var generoPrincipal: String?,
    var multijugador: Boolean?,
    var longitud: Double?,
    var latitud:Double?,
    ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeValue(recaudacion)
        parcel.writeString(fechaSalida)
        parcel.writeString(generoPrincipal)
        parcel.writeValue(multijugador)
        parcel.writeValue(longitud)
        parcel.writeValue(latitud)
    }

    override fun describeContents(): Int {
        return 0
    }
    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return "Uid: $id \n" +
                "Nombre: $nombre \n" +
                "Recaudación: $recaudacion \n" +
                "Fecha de salida: ${fechaSalida} \n" +
                "Género: $generoPrincipal \n" +
                "Multijugador: $multijugador \n"+
                "Logitud: $longitud \n" +
                "Latitud: $latitud \n"

    }

    companion object CREATOR : Parcelable.Creator<Videojuego> {
        override fun createFromParcel(parcel: Parcel): Videojuego {
            return Videojuego(parcel)
        }

        override fun newArray(size: Int): Array<Videojuego?> {
            return arrayOfNulls(size)
        }
    }


}