package com.example.moviles_software_2021_a

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

class BListView : AppCompatActivity() {
    var posicionItemSeleccionado=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val arregloNumeros = BBaseDatosMemoria.arregloBEntrenador
        val adaptador= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            arregloNumeros// Arreglo

        )
        val listViewEjemplo= findViewById<ListView>(R.id.txv_ejemplo)
        listViewEjemplo.adapter=adaptador
        //debemos actualizar la interfaz para que se agrege de manera correct
        val botonListView=findViewById<Button>(R.id.btn_list_view_anadir)
        botonListView.setOnClickListener{
            anadirItemsAListView( BEntrenador("Kevin","k@s.com",null),
                arregloNumeros,
                adaptador)
        }

        listViewEjemplo.setOnItemLongClickListener {
                adapterView,view,posicion,id->
            Log.i("list_view","Dio clic ${posicion}")

            val builder=AlertDialog.Builder(this)
            builder.setTitle("Titulo")
            //builder.setMessage("Mensaje")
            val seleccionUsuario= booleanArrayOf(
                true,
                false,
                false
            )
            val opciones=resources.getStringArray(R.array.string_array_opciones_dialogo)
            builder.setMultiChoiceItems(
                opciones,
                seleccionUsuario,
                {dialog,which, isChecked->
                    Log.i("list_view","${which} ${isChecked}")

                }
            )

            builder.setPositiveButton(
                "Si",
                {dialog, which->
                    Log.i("list-view","Si")
                }
            )
            builder.setNegativeButton(
                "No",
                null
            )
            val dialog=builder.create()
            dialog.show()


            return@setOnItemLongClickListener true
        }
            //registerForContextMenu(listViewEjemplo)



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
        Log.i("List-view","List View ${posicionItemSeleccionado}")
        Log.i("List-view","Entrenador ${BBaseDatosMemoria.arregloBEntrenador[id]}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            //EDITAR
            R.id.mi_editar->{
                Log.i(
                    "List-view","Editar ${
                        BBaseDatosMemoria.arregloBEntrenador[
                                posicionItemSeleccionado
                        ]
                    }"
                )
                return true
            }
            //ELIMINAR
            R.id.mi_eliminar->{
                Log.i(
                    "List-view","eliminar ${
                        BBaseDatosMemoria.arregloBEntrenador[
                                posicionItemSeleccionado
                        ]
                    }"
                )
                return true
            }
            else-> super.onContextItemSelected(item)
        }
    }
    fun anadirItemsAListView(
        valor: BEntrenador,
        arreglo: ArrayList<BEntrenador>,
        adaptador: ArrayAdapter<BEntrenador>,
    ){
        arreglo.add(valor)
        adaptador.notifyDataSetChanged()
    }
}