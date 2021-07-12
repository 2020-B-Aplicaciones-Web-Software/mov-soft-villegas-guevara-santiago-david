package com.example.trabajoenclase01

import android.os.Parcel
import android.os.Parcelable

class EUsuarioBBD (
    var id: Int,
    var nombre: String?,
    var descripcion: String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "Nombre: ${nombre}: Descripción: ${descripcion}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EUsuarioBBD> {
        override fun createFromParcel(parcel: Parcel): EUsuarioBBD {
            return EUsuarioBBD(parcel)
        }

        override fun newArray(size: Int): Array<EUsuarioBBD?> {
            return arrayOfNulls(size)
        }
    }


}