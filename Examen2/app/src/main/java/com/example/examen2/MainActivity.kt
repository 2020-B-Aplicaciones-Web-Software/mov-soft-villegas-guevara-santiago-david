package com.example.examen02

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    var posicionItemSeleccionado=0
    var listaEmpresas= arrayListOf<EmpresaDesarrolladora>()
    lateinit var  adaptadorEmpresa: ArrayAdapter<EmpresaDesarrolladora>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("Empresas")
        val botonCrearEmpresa = findViewById<Button>(
            R.id.btn_crearEmpresa
        )
        cargarEmpresas()
        botonCrearEmpresa.setOnClickListener{
            abrirActividad(
                CrearEmpresa::class.java
            )
        }


    }
    fun cargarInterfaz(){


        adaptadorEmpresa= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            listaEmpresas// Arreglo

        )
        val listViewEmpresa= findViewById<ListView>(R.id.list_Empresas)
        listViewEmpresa.adapter=adaptadorEmpresa
        registerForContextMenu(listViewEmpresa)



    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater=menuInflater
        inflater.inflate(R.menu.menuempresa,menu)
        val info=menuInfo as AdapterView.AdapterContextMenuInfo
        val id=info.position
        posicionItemSeleccionado=id



    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            //EDITAR
            R.id.Item_Deditar->{
                abrirActividadConParametros(
                    EditarEmpresa::class.java,
                    listaEmpresas[posicionItemSeleccionado]
                )





                return true
            }
            //ELIMINAR
            R.id.Item_Deliminar->{
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                builder.setMessage("¿Está seguro de que desea eliminar a la empresa desarrolladora seleccionada?")


                builder.setPositiveButton(
                    "Si",
                    {dialog, which->
                        val selecccion: Int? =listaEmpresas[posicionItemSeleccionado].id
                        if (selecccion != null) {

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
            //Videojuego
            R.id.Item_DverVideojuego->{
                abrirActividadConParametros(
                    ListaVideojuegos::class.java,
                    listaEmpresas[posicionItemSeleccionado]
                )

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
    fun abrirActividad(
        clase: Class<*>
    ){
        val intentExplicito= Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }

    fun cargarEmpresas(){

        val db= Firebase.firestore


        val referenciaRestaurante=db
            .collection("EmpresaDesarroladora")
        referenciaRestaurante
            .get()
            .addOnSuccessListener { empresas ->
                for (empresa in empresas) {

                    val empresaCargada = empresa.toObject(EmpresaDesarrolladora::class.java)


                    if (empresaCargada != null) {

                        listaEmpresas.add(
                            empresaCargada
                        )

                    }

                }

                cargarInterfaz()
            }





    }
}
