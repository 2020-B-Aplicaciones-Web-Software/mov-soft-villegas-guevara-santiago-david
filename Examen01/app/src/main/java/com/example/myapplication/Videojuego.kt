package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class Videojuego(
    var id:Int?,
    var nombre:String?,
    var recaudacion:Double?,
    var fechaSalida: Date?,
    var generoPrincipal: String?,
    var multijugador: Boolean?,
    var empresaId: Int?

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Date::class.java.classLoader) as? Date,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeValue(recaudacion)
        parcel.writeValue(fechaSalida)
        parcel.writeString(generoPrincipal)
        parcel.writeValue(multijugador)
        parcel.writeValue(empresaId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return "Nombre: $nombre \n" +
                "Recaudación: $recaudacion \n" +
                "Fecha de salida: ${sdf.format(fechaSalida)} \n" +
                "Género: $generoPrincipal \n" +
                "Multijugador: $multijugador \n"
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