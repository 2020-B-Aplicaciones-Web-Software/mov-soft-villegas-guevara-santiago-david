package com.example.deber02

import android.os.Parcel
import android.os.Parcelable

class Videojuego(
    val nombre: String?,
    val plataforma:Int,
    val precio:Double,
    val descuento:Double,
    val portada:Int,
    val informacion:Int,
    val fechaLanzamiento: String?,
    val Desarrollador: String?,
    val Distruibidor: String?,
    val confMinima: String?,
    val confRecomendada: String?


):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(plataforma)
        parcel.writeDouble(precio)
        parcel.writeDouble(descuento)
        parcel.writeInt(portada)
        parcel.writeInt(informacion)
        parcel.writeString(fechaLanzamiento)
        parcel.writeString(Desarrollador)
        parcel.writeString(Distruibidor)
        parcel.writeString(confMinima)
        parcel.writeString(confRecomendada)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Videojuego> {
        val arregloVideojuego= arrayListOf<Videojuego>()
        init {
            arregloVideojuego
                .add(
                    Videojuego("Days Gone",R.drawable.ic_origin,50.00
                        ,53.00,R.drawable.ic_daysgone,1,"18 mayo 2021"
                        ,"Bend Studio", "PlaySation","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("RE Village",R.drawable.ic_play,60.00
                        ,40.00,R.drawable.ic_residentvillage,1,"07 mayo 2021"
                        ,"Bend Studio", "PlaySation","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Monster Hunter Stories 2",R.drawable.ic_switch,60.00
                        ,45.00,R.drawable.ic_monsterhunter,1,"09 julio 2021"
                        ,"Capcom", "Capcom","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Gears of War: Ultimate Edition Xbox ONE",
                        R.drawable.ic_xboxlog,50.00
                        ,73.00,R.drawable.ic_gearsremake,1,"28 agosto 2015"
                        ,"Coalition", "Xbox","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("God of war",R.drawable.ic_play,20.00
                        ,0.00,R.drawable.ic_god,1,"20 abril 2018"
                        ,"Santa Monica", "PlaySation","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Overcooked! 2",R.drawable.ic_switch,25.00
                        ,59.00,R.drawable.ic_sobrecocinado2,1,"07 agosto 2018"
                        ,"Ghost Town Games" , "Team17","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Days Gone",R.drawable.ic_origin,50.00
                        ,53.00,R.drawable.ic_daysgone,1,"18 mayo 2021"
                        ,"Bend Studio", "PlaySation","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("RE Village",R.drawable.ic_play,60.00
                        ,40.00,R.drawable.ic_residentvillage,1,"07 mayo 2021"
                        ,"Bend Studio", "PlaySation","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Monster Hunter Stories 2",R.drawable.ic_switch,60.00
                        ,45.00,R.drawable.ic_monsterhunter,1,"09 julio 2021"
                        ,"Capcom", "Capcom","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Gears of War: Ultimate Edition Xbox ONE",
                        R.drawable.ic_xboxlog,50.00
                        ,73.00,R.drawable.ic_gearsremake,1,"28 agosto 2015"
                        ,"Coalition", "Xbox","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("God of war",R.drawable.ic_play,20.00
                        ,50.00,R.drawable.ic_god,1,"20 abril 2018"
                        ,"Santa Monica", "PlaySation","","")
                )
            arregloVideojuego
                .add(
                    Videojuego("Overcooked! 2",R.drawable.ic_switch,25.00
                        ,59.00,R.drawable.ic_sobrecocinado2,1,"07 agosto 2018"
                        ,"Ghost Town Games" , "Team17","","")
                )

        }
        override fun createFromParcel(parcel: Parcel): Videojuego {
            return Videojuego(parcel)
        }

        override fun newArray(size: Int): Array<Videojuego?> {
            return arrayOfNulls(size)
        }
    }
}