package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.*

class ListaVideojuegos : AppCompatActivity() {

    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    var posicionItemSeleccionado=0
    var listaVideojuego= arrayListOf<Videojuego>()
    val instanciaVideojuegoBDD=ESqliteHelperVideojuego(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_videojuegos)
        this.setTitle("Videojuegos")
        val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")


        val txtEmpresa=findViewById<TextView>(
            R.id.textView_Empresa
        )
        val botonCrearVideojuego = findViewById<Button>(
            R.id.btn_crearVideojuego
        )
        txtEmpresa.setText(empresa?.nombre)
        val botonRegresar = findViewById<Button>(
            R.id.btn_regresar
        )


        if (empresa != null) {
            listaVideojuego=instanciaVideojuegoBDD.consultarVideojuegosPorEmpresa(empresa.id!!)
        }

        val adaptador= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            listaVideojuego// Arreglo

        )
        val listViewVideojuegos= findViewById<ListView>(R.id.list_Videojuegos)
        listViewVideojuegos.adapter=adaptador
        registerForContextMenu(listViewVideojuegos)
        botonCrearVideojuego.setOnClickListener {
            if (empresa != null) {
                abrirActividadConParametros(
                    CrearVideojuego::class.java,
                    empresa
                )
            }

        }
        botonRegresar.setOnClickListener {
            abrir(MainActivity::class.java)
        }



    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater=menuInflater
        inflater.inflate(R.menu.menuvideojuego,menu)
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        posicionItemSeleccionado=id



    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            //EDITAR
            R.id.Item_VEditar->{
                val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")
                if (empresa != null) {
                    abrirActividadConParametrosJuego(
                        EditarVideojuego::class.java,
                        listaVideojuego[posicionItemSeleccionado],
                        empresa

                    )
                }




                return true
            }
            //ELIMINAR
            R.id.Item_VEliminar->{
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                builder.setMessage("¿Está seguro de que desea eliminar el videojuego seleccionado?")


                builder.setPositiveButton(
                    "Si",
                    {dialog, which->
                        val selecccion: Int? =listaVideojuego[posicionItemSeleccionado].id
                        if (selecccion != null) {

                            instanciaVideojuegoBDD.eliminarVideojuegoFormulario(selecccion)
                        }
                        val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")
                        if (empresa != null) {
                            listaVideojuego=instanciaVideojuegoBDD.consultarVideojuegosPorEmpresa(empresa.id!!)
                        }

                        val adaptador= ArrayAdapter(
                            this,//Contexto
                            android.R.layout.simple_list_item_1,//Layout (visual)
                            listaVideojuego// Arreglo

                        )
                        val listViewVideojuego= findViewById<ListView>(R.id.list_Videojuegos)
                        listViewVideojuego.adapter=adaptador

                    }
                )
                builder.setNegativeButton(
                    "No",
                    null
                )
                val dialog=builder.create()
                dialog.show()

                return true
            }


            else-> super.onContextItemSelected(item)
        }
    }


    fun abrirActividadConParametros(
        clase: Class<*>,
        empresa: EmpresaDesarrolladora
    ) {
        val intentExplicito = Intent(this, clase)

        intentExplicito.putExtra("Empresa", empresa)
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }
    fun abrir(
        clase: Class<*>
    ){

        val intentExplicito=Intent(this, clase)
        startActivity(intentExplicito)
    }

    fun abrirActividadConParametrosJuego(
        clase: Class<*>,
        juego: Videojuego,
        empresa: EmpresaDesarrolladora
    ) {
        val intentExplicito = Intent(this, clase)

        intentExplicito.putExtra("Juego", juego)
        intentExplicito.putExtra("Empresa", empresa)
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

}
