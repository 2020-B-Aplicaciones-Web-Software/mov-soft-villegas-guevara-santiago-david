package com.example.trabajoenclase01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    var posicionItemSeleccionado=0
    val instanciaBDD=ESqliteHelperUsuario(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ESqliteHelperUsuario.ListaUsuario= instanciaBDD.consultarUsuarios()
        val adaptador= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            ESqliteHelperUsuario.ListaUsuario// Arreglo

        )
        val listViewUsuario= findViewById<ListView>(R.id.list_Usuario)
        listViewUsuario.adapter=adaptador
        registerForContextMenu(listViewUsuario)

        val botoIrAgregarUsuario=findViewById<Button>(
            R.id.btn_crearUsuario
        )
        botoIrAgregarUsuario.setOnClickListener{
            abrirActividad(
                AgregarUsuario::class.java
            )
        }

    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        posicionItemSeleccionado=id



    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            //EDITAR
            R.id.mi_editar->{

                abrirActividadConParametros(
                    EditarUsuario::class.java,
                    ESqliteHelperUsuario.ListaUsuario[
                            posicionItemSeleccionado
                    ])


                return true
            }
            //ELIMINAR
            R.id.mi_eliminar->{


                instanciaBDD.eliminarUsuarioFormulario(ESqliteHelperUsuario.ListaUsuario[
                        posicionItemSeleccionado
                ].id)
                ESqliteHelperUsuario.ListaUsuario= instanciaBDD.consultarUsuarios()
                //abrirActividad(MainActivity::class.java)
                val adaptador= ArrayAdapter(
                    this,//Contexto
                    android.R.layout.simple_list_item_1,//Layout (visual)
                    ESqliteHelperUsuario.ListaUsuario// Arreglo

                )
                val listViewUsuario= findViewById<ListView>(R.id.list_Usuario)
                listViewUsuario.adapter=adaptador




                return true
            }
            else-> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: EUsuarioBBD
    ) {
        val intentExplicito = Intent(this,clase)

        intentExplicito.putExtra("Usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

    fun abrirActividad(
        clase: Class<*>
    ){

        val intentExplicito= Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }



}