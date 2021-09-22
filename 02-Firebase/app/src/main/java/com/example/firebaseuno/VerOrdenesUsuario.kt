package com.example.firebaseuno

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerOrdenesUsuario : AppCompatActivity() {
    var query: Query?=null

    lateinit var  adaptadorOrden: ArrayAdapter<OrdenFirebase>
    val calificar:List<Int> = listOf(3,2,1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ordenes_usuario)
        BaseDeDatosMemoria.listaPedido.clear()
        cargarPedidos()
    }
    fun cargarPedidos(){
        val db = Firebase.firestore
        val ref0rdenes = db.collection("orden")
            .whereEqualTo("estado", "Enviado")
            .whereEqualTo("usuario", BAuthUsuario.usuario!!.email)
            .orderBy("fechaPedido", Query.Direction.DESCENDING)
            .limit(2)
        var tarea: Task<QuerySnapshot>? = null
        //verificamos si es la consulta por primera vez o por segunda vez
        if(query == null){
            tarea = ref0rdenes.get()
        } else{
            tarea = query!!.get()
        }
        if (tarea != null) {
            tarea
                .addOnSuccessListener { ordenes ->

                    ordenes.forEach {
                        guardarQuery(ordenes, ref0rdenes)
                        val productos = it.data["productos"] as ArrayList<HashMap<String, Any>>

                        val listaProductos = productos.map {
                            ProductoOrdenFirebase(
                                it["nombreProducto"].toString(),
                                it["precio"].toString().toDouble(),
                                it["cantidad"].toString().toInt(),
                                it["uid"].toString()
                            )
                        }
                        BaseDeDatosMemoria.listaPedido.add(
                            OrdenFirebase(
                                it["fechaPedido"].toString(),
                                it["total"].toString().toDouble(),
                                it["calificacion"].toString(),
                                it["estado"].toString(),
                                RestauranteOrdenFirebase(
                                    it["restaurante.nombre"].toString(),
                                    it["restaurante.califacionPromedio"].toString().toDouble(),
                                    it["restaurante.uid"].toString()
                                ),
                                listaProductos,
                                it.id,
                                it["usuario"].toString()

                            )
                        )
                    }

                    cargarInterfaz()

                }


                .addOnFailureListener {}
        }
    }



    fun guardarQuery(documentSnapshot: QuerySnapshot, refCities:Query){
        if(documentSnapshot.size() > 0){
            val ultimoDocumento = documentSnapshot.documents[documentSnapshot.size()-1]
            query = refCities
                .startAfter(ultimoDocumento)
        }else{

        }
    }
    fun cargarInterfaz(){
        adaptadorOrden= ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout (visual)
            BaseDeDatosMemoria.listaPedido// Arreglo

        )
        val botonCargar=findViewById<Button>(R.id.btn_cargar)
        val listaOrden=findViewById<ListView>(R.id.lv_lista_productosUsuario)
        listaOrden
            .setOnItemLongClickListener { parent, view, position, id ->
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Califique el servicio?")

                val opciones = resources.getStringArray(R.array.string_array_opciones_calificacion)
                var calificacion:Int=3
                builder.setSingleChoiceItems(
                    opciones,
                    0,
                    { dialog, which ->
                        calificacion=calificar[which]

                    }
                )

                builder.setPositiveButton(
                    "Calificar",
                    DialogInterface.OnClickListener { dialog, which ->
                        cambiarEstado(BaseDeDatosMemoria.listaPedido[position].uid,
                        calificacion)

                        calcularCalificacionPromedio(BaseDeDatosMemoria.listaPedido[position].restaurante.uid,
                        calificacion)

                        BaseDeDatosMemoria.listaPedido.removeAt(position)
                        adaptadorOrden.notifyDataSetChanged()


                    }
                )

                builder.setNegativeButton("Cancelar", null)

                val dialogo = builder.create()
                dialogo.show()
                return@setOnItemLongClickListener true
            }
        listaOrden.adapter=adaptadorOrden
        botonCargar.setOnClickListener {
            cargarPedidos()

        }
    }
    fun cambiarEstado(documento:String,calificacion:Int){

        val db = Firebase.firestore
        val referenciaOrden=db.collection("orden").document(documento)

        db.runTransaction { transaction ->
            transaction.update(referenciaOrden, mapOf(
                "calificacion" to calificacion,
                "estado" to "Entregado"

            ))


        }


    }
    fun calcularCalificacionPromedio(documento: String,calificacion: Int){
        val db = Firebase.firestore
        val referenciaOrden=db.collection("restaurante").document(documento)
        db.runTransaction {
                transaction ->
            val documentoActual=transaction.get(referenciaOrden)
            val promedio=documentoActual.getDouble("calificacionPromedio")
            val suma=documentoActual.get("sumatoriaCalificaciones").toString().toDouble()
            val usuario=documentoActual.get("usuariosCalicado").toString().toDouble()
            if(promedio !=null){
                val nuevaSuma=suma+calificacion
                val nuevoUsuario=usuario+1
                val nuevoPromedio:Double= (nuevaSuma/nuevoUsuario)
                transaction.update(referenciaOrden,
                    mapOf(
                        "calificacionPromedio" to nuevoPromedio,
                    "sumatoriaCalificaciones" to nuevaSuma,
                        "usuariosCalicado" to nuevoUsuario)
                )
            }

        }

    }

}