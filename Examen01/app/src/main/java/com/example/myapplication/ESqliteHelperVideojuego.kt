package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.ArrayList

class ESqliteHelperVideojuego(contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "moviles1",
    null,
    1

)
{


    override fun onCreate(db: SQLiteDatabase?) {

        val scriptCrearTablaUsuario=
            """
            CREATE TABLE VIDEOJUEGO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                recaudacion DOUBLE,
                fechaSalida VARCHAR(15),
                generoPrincipal VARCHAR(50),
                multijugador VARCHAR(5),
                empresaId INTEGER,
                FOREIGN KEY (empresaId) REFERENCES EMPRESA (id) 
                ON UPDATE CASCADE
                ON DELETE CASCADE
                
            )
            """.trimIndent()
        Log.i("bbd","Creando la tabla empresa desarrolladora")
        db?.execSQL(scriptCrearTablaUsuario)

    }



    fun crearVideojuegoFormulario(
        nombre: String,
        recaudacion: Double,
        fechaSalida: String,
        generoPrincipal: String,
        multijugador: String,
        empresaId: Int

    ):Boolean{
        val conexionEscritura=writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("recaudacion",recaudacion)
        valoresAGuardar.put("fechaSalida",fechaSalida)
        valoresAGuardar.put("generoPrincipal",generoPrincipal)
        valoresAGuardar.put("multijugador",multijugador)
        valoresAGuardar.put("empresaId",empresaId)

        val resultadoEscritura: Long=conexionEscritura.
        insert(
            "VIDEOJUEGO",
            null,
            valoresAGuardar
        )
        conexionEscritura.close()
        return if(resultadoEscritura.toInt()==-1) false else true

    }

    fun consultarVideojuegosPorEmpresa(id:Int): ArrayList<Videojuego> {
        val scriptConsultaUsuario="SELECT*FROM VIDEOJUEGO WHERE empresaId==${id}"
        val baseDatosLectura= readableDatabase
        //esto es como un inde del arreglo
        val resultaConsultaLectura= baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            null
        )
        val existeEmpresa= resultaConsultaLectura.moveToFirst()

        val arregloVideojuego= arrayListOf<Videojuego>()
        if(existeEmpresa){
            do{


                val formato = SimpleDateFormat("dd/MM/yyyy")
                val id=resultaConsultaLectura.getInt(0)
                val nombre=resultaConsultaLectura.getString(1)
                val recaudacion = resultaConsultaLectura.getDouble(2)
                val fechaSalida=resultaConsultaLectura.getString(3)
                val generoPrincipal=resultaConsultaLectura.getString(4)
                val multijugador=resultaConsultaLectura.getString(5)
                val empresaId=resultaConsultaLectura.getInt(6)
                val fecha = formato.parse(fechaSalida)
                val multi = java.lang.Boolean.parseBoolean(multijugador)
                if(id!=null){
                    arregloVideojuego.add(
                        Videojuego(id,nombre,recaudacion,fecha,generoPrincipal,multi,empresaId)
                    )

                }

            }while (resultaConsultaLectura.moveToNext())

        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloVideojuego

    }
    fun eliminarVideojuegoFormulario(id: Int):Boolean{
        val conexionEscritura=writableDatabase
        val resultadoEliminado= conexionEscritura
            .delete(
                "VIDEOJUEGO",//Tabla
                "id=?",//Clausula Where
                arrayOf(
                    id.toString(),
                )//Areglo ordenados de parametros
            )
        conexionEscritura.close()
        return if (resultadoEliminado.toInt()==-1)false else true
    }

    fun eliminarVideojuegosDeEmpresa(id: Int):Boolean{
        val conexionEscritura=writableDatabase
        val resultadoEliminado= conexionEscritura
            .delete(
                "VIDEOJUEGO",//Tabla
                "empresaId=?",//Clausula Where
                arrayOf(
                    id.toString(),
                )//Areglo ordenados de parametros
            )
        conexionEscritura.close()
        return if (resultadoEliminado.toInt()==-1)false else true
    }

    fun actualizarVideojuegoFormulario(
        nombre: String,
        recaudacion: Double,
        fechaSalida: String,
        generoPrincipal: String,
        multijugador: String,
        empresaId: Int,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura= writableDatabase

        val valoresAActualizar= ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("recaudacion",recaudacion)
        valoresAActualizar.put("fechaSalida",fechaSalida)
        valoresAActualizar.put("generoPrincipal",generoPrincipal)
        valoresAActualizar.put("multijugador",multijugador)
        valoresAActualizar.put("empresaId",empresaId)


        val resultadoActualizacion= conexionEscritura.
        update(
            "VIDEOJUEGO",
            valoresAActualizar,
            "id=?",
            arrayOf(
                idActualizar.toString()
            )
        )
        conexionEscritura.close()
        return  if(resultadoActualizacion==-1)false else true

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}