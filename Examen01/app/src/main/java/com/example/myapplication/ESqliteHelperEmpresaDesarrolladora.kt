package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class ESqliteHelperEmpresaDesarrolladora (contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario=
            """
            CREATE TABLE EMPRESA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                numeroTrabajadores INTEGER,
                fechaFundacion VARCHAR(15),
                pais VARCHAR(50),
                independiente VARCHAR(5)
                
            )
            """.trimIndent()
        Log.i("bbd","Creando la tabla empresa desarrolladora")
        db?.execSQL(scriptCrearTablaUsuario)

    }

    fun crearEmpresaFormulario(
        nombre: String,
        numeroTrabajadores: Int,
        fechaFundacion: String,
        pais: String,
        independiente: String

        ):Boolean{
        val conexionEscritura=writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("numeroTrabajadores",numeroTrabajadores)
        valoresAGuardar.put("fechaFundacion",fechaFundacion)
        valoresAGuardar.put("pais",pais)
        valoresAGuardar.put("independiente",independiente)

        val resultadoEscritura: Long=conexionEscritura.
        insert(
            "EMPRESA",
            null,
            valoresAGuardar
        )
        conexionEscritura.close()
        return if(resultadoEscritura.toInt()==-1) false else true

    }

    fun consultarEmpresas():ArrayList<EmpresaDesarrolladora>{
        val scriptConsultaUsuario="SELECT*FROM EMPRESA"
        val baseDatosLectura= readableDatabase
        //esto es como un inde del arreglo
        val resultaConsultaLectura= baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            null
        )
        val existeEmpresa= resultaConsultaLectura.moveToFirst()

        val arregloEmpresa= arrayListOf<EmpresaDesarrolladora>()
        if(existeEmpresa){
            do{

                val formato = SimpleDateFormat("dd/MM/yyyy")
                val id=resultaConsultaLectura.getInt(0)
                val nombre=resultaConsultaLectura.getString(1)
                val numeroTrabajadores = resultaConsultaLectura.getInt(2)
                val fechaFundacion=resultaConsultaLectura.getString(3)
                val pais=resultaConsultaLectura.getString(4)
                val independiente=resultaConsultaLectura.getString(5)
                val fecha = formato.parse(fechaFundacion)
                val indie = java.lang.Boolean.parseBoolean(independiente)
                if(id!=null){
                    arregloEmpresa.add(
                        EmpresaDesarrolladora(id,nombre,numeroTrabajadores,fecha,pais,indie)
                    )

                }

            }while (resultaConsultaLectura.moveToNext())

        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloEmpresa

    }


    fun eliminarEmpresaFormulario(id: Int):Boolean{
        val conexionEscritura=writableDatabase
        val resultadoEliminado= conexionEscritura
            .delete(
                "EMPRESA",//Tabla
                "id=?",//Clausula Where
                arrayOf(
                    id.toString(),
                )//Areglo ordenados de parametros
            )
        conexionEscritura.close()
        return if (resultadoEliminado.toInt()==-1)false else true
    }

    fun actualizarUsuarioFormulario(
        nombre: String,
        numeroTrabajadores: Int,
        fechaFundacion: String,
        pais: String,
        independiente: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura= writableDatabase

        val valoresAActualizar= ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("numeroTrabajadores",numeroTrabajadores)
        valoresAActualizar.put("fechaFundacion",fechaFundacion)
        valoresAActualizar.put("pais",pais)
        valoresAActualizar.put("independiente",independiente)


        val resultadoActualizacion= conexionEscritura.
        update(
            "EMPRESA",
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