package com.example.examen02

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*


class EmpresaDesarrolladora(
    var id: Int?,
    var nombre: String?,
    var numeroTrabajadores: Int?,
    var fechaFundacion: String?,
    var pais: String?,
    var independiente: Boolean?,

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeValue(numeroTrabajadores)
        parcel.writeString(fechaFundacion)
        parcel.writeString(pais)
        parcel.writeValue(independiente)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmpresaDesarrolladora> {
        override fun createFromParcel(parcel: Parcel): EmpresaDesarrolladora {
            return EmpresaDesarrolladora(parcel)
        }

        override fun newArray(size: Int): Array<EmpresaDesarrolladora?> {
            return arrayOfNulls(size)
        }
    }
    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        return "Nombre: $nombre \n" +
                "Número de trabajadores: $numeroTrabajadores\n" +
                "Fecha de fundación: $fechaFundacion\n" +
                "País: $pais\n" +
                "Independiente: $independiente"
    }


}