package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmpresaDesarrolladora(
    var id: Int?,
    var nombre: String?,
    var numeroTrabajadores: Int?,
    var fechaFundacion: Date?,
    var pais: String?,
    var independiente: Boolean?,

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Date::class.java.classLoader) as? Date,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeValue(numeroTrabajadores)
        parcel.writeValue(fechaFundacion)
        parcel.writeString(pais)
        parcel.writeValue(independiente)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        return "Nombre: $nombre \n" +
                "Número de trabajadores: $numeroTrabajadores\n" +
                "Fecha de fundación: ${sdf.format(fechaFundacion)}\n" +
                "País: $pais\n" +
                "Independiente: $independiente"
    }

    companion object CREATOR : Parcelable.Creator<EmpresaDesarrolladora> {
        override fun createFromParcel(parcel: Parcel): EmpresaDesarrolladora {
            return EmpresaDesarrolladora(parcel)
        }

        override fun newArray(size: Int): Array<EmpresaDesarrolladora?> {
            return arrayOfNulls(size)
        }
    }


}