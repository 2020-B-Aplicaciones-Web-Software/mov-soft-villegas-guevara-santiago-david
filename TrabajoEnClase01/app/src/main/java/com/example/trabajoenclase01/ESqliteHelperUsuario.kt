package com.example.trabajoenclase01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.telephony.euicc.EuiccInfo
import android.util.Log

class ESqliteHelperUsuario(
    contexto: Context?
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
            CREATE TABLE USUARIO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion varchar(50)
            )
            """.trimIndent()
        Log.i("bbd","Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)

    }

     fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String

    ):Boolean{
        val conexionEscritura=writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)
        val resultadoEscritura: Long=conexionEscritura.
        insert(
            "USUARIO",
            null,
            valoresAGuardar
        )
        conexionEscritura.close()
        return if(resultadoEscritura.toInt()==-1) false else true

    }
    fun consultarUsuarioPorId(id:Int): EUsuarioBBD{
        val scriptConsultaUsuario="SELECT*FROM USUARIO WHERE ID=${id}"
        val baseDatosLectura= readableDatabase
        //esto es como un inde del arreglo
        val resultaConsultaLectura= baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            null
        )
        val existeUsuario= resultaConsultaLectura.moveToFirst()
        val usuarioEncontrado= EUsuarioBBD(0,"","")
        val arregloUsuario= arrayListOf<EUsuarioBBD>()
        if(existeUsuario){
            do{
                val id=resultaConsultaLectura.getInt(0)
                val nombre=resultaConsultaLectura.getString(1)
                val descripcion = resultaConsultaLectura.getString(2)
                if(id!=null){
                    //arregloUsuario.add(
                    //EUsuarioBBD(id,nombre,descripcion)
                    //)
                    usuarioEncontrado.id=id
                    usuarioEncontrado.nombre=nombre
                    usuarioEncontrado.descripcion=descripcion
                }

            }while (resultaConsultaLectura.moveToNext())

        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado

    }

    fun consultarUsuarios():ArrayList<EUsuarioBBD>{
        val scriptConsultaUsuario="SELECT*FROM USUARIO"
        val baseDatosLectura= readableDatabase
        //esto es como un inde del arreglo
        val resultaConsultaLectura= baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            null
        )
        val existeUsuario= resultaConsultaLectura.moveToFirst()

        val arregloUsuario= arrayListOf<EUsuarioBBD>()
        if(existeUsuario){
            do{
                val id=resultaConsultaLectura.getInt(0)
                val nombre=resultaConsultaLectura.getString(1)
                val descripcion = resultaConsultaLectura.getString(2)
                if(id!=null){
                    arregloUsuario.add(
                    EUsuarioBBD(id,nombre,descripcion))

                }

            }while (resultaConsultaLectura.moveToNext())

        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloUsuario

    }
    fun eliminarUsuarioFormulario(id: Int):Boolean{
        val conexionEscritura=writableDatabase
        val resultadoEliminado= conexionEscritura
            .delete(
                "USUARIO",//Tabla
                "id=?",//Clausula Where
                arrayOf(
                    id.toString(),
                )//Areglo ordenados de parametros
            )
        conexionEscritura.close()
        return if (resultadoEliminado.toInt()==-1)false else true
    }

    fun actualizarUsuarioFormulario(
        nombre:String,
        descripcion:String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura= writableDatabase

        val valoresAActualizar= ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion", descripcion)

        val resultadoActualizacion= conexionEscritura.
        update(
            "USUARIO",
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
    companion object{
        var ListaUsuario= arrayListOf<EUsuarioBBD>()


    }
}