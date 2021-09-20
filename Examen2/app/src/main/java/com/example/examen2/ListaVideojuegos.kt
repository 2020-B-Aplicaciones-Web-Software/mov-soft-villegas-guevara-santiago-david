package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ListaVideojuegos : AppCompatActivity() {

    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    var posicionItemSeleccionado=0
    var listaVideojuego= arrayListOf<Videojuego>()
    lateinit var empresa: EmpresaDesarrolladora
    lateinit var  adaptadorJuego: ArrayAdapter<Videojuego>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_videojuegos)
        this.setTitle("Videojuegos")
        empresa= intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")!!
        cargarVideojuegos()


    }
    fun cargarInterfaz(){
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



        adaptadorJuego= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            listaVideojuego// Arreglo

        )
        val listViewVideojuegos= findViewById<ListView>(R.id.list_Videojuegos)
        listViewVideojuegos.adapter=adaptadorJuego
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
    fun cargarVideojuegos(){
        val db= Firebase.firestore


        val referenciaVideojuego=db
            .collection("EmpresaDesarroladora").document(empresa.id!!)
            .collection("Videojuego")
        referenciaVideojuego
            .get()
            .addOnSuccessListener { videojuegos ->
                for (videojuego in videojuegos) {


                    if (videojuego != null) {

                        listaVideojuego.add(
                            Videojuego(videojuego.id,
                                videojuego["nombre"].toString(),
                                videojuego["recaudacion"].toString().toDouble(),
                                videojuego["fechaSalida"].toString(),
                                videojuego["generoPrincipal"].toString(),
                                videojuego["multijugador"].toString().toBoolean(),
                                videojuego["longitud"].toString().toDouble(),
                                videojuego["latitud"].toString().toDouble(),
                            )
                        )

                    }

                }

                cargarInterfaz()
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
            //MAPA
            R.id.Item_VMapa->{
                val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")
                if (empresa != null) {
                    abrirActividadConParametrosJuego(
                        MapaVideojuego::class.java,
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
                        val selecccion: String? =listaVideojuego[posicionItemSeleccionado].id
                        if (selecccion != null) {
                            val db= Firebase.firestore

                            val referenciaJuego=db
                                .collection("EmpresaDesarroladora").document(empresa.id!!)
                                .collection("Videojuego").document(selecccion)
                            referenciaJuego.delete()
                                .addOnSuccessListener {
                                    listaVideojuego.removeAt(posicionItemSeleccionado)
                                    adaptadorJuego.notifyDataSetChanged()
                                }
                                .addOnFailureListener{}


                        }


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