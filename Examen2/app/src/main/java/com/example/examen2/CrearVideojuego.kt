package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearVideojuego : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_videojuego)
        this.setTitle("Crear videojuego")
        val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")

        val txtNombre=findViewById<EditText>(
            R.id.editText_nombreVideojuego
        )
        val txtRecaudacion=findViewById<EditText>(
            R.id.editText_recaudacion
        )
        val txtFecha=findViewById<EditText>(
            R.id.editText_FechaSalida
        )
        val txtGenero=findViewById<EditText>(
            R.id.editText_genero
        )
        val txtMultijugador=findViewById<EditText>(
            R.id.editText_multijugador
        )
        val textLongitud=findViewById<EditText>(
            R.id.editText_longitud
        )
        val textLatitud=findViewById<EditText>(
            R.id.editText_latitud
        )
        val msg=findViewById<TextView>(
            R.id.textView_msgCrearVideojuego
        )

        val botoAgregarVideojuego=findViewById<Button>(
            R.id.btn_crearVideojuegoC
        )

        botoAgregarVideojuego.setOnClickListener{
            if(Verificadores.Companion.validadorStrings(txtNombre.getText().toString())&&
                Verificadores.Companion.validadorSaldo(txtRecaudacion.getText().toString())&&
                Verificadores.Companion.validadorStrings(txtGenero.getText().toString())&&
                Verificadores.Companion.validadorBoleano(txtMultijugador.getText().toString())&&
                Verificadores.Companion.validadorFecha(txtFecha.getText().toString())&&
                Verificadores.Companion.validarLongitud(textLongitud.getText().toString())&&
                Verificadores.Companion.validarLatitud(textLatitud.getText().toString())

            ) {
                msg.setText("")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                builder.setMessage("¿Está seguro de que desea crear este Videojuego?")


                builder.setPositiveButton(
                    "Si",
                    { dialog, which ->
                        if (empresa != null) {
                            val nuevoVideojuego = hashMapOf<String, Any>(
                                "nombre" to txtNombre.getText().toString(),
                                "recaudacion" to txtRecaudacion.getText().toString().toDouble(),
                                "fechaSalida" to txtFecha.getText().toString(),
                                "generoPrincipal" to txtGenero.getText().toString(),
                                "multijugador" to txtMultijugador.getText().toString(),
                                "longitud" to textLongitud.getText().toString(),
                                "latitud" to textLatitud.getText().toString()
                            )
                            val db = Firebase.firestore
                            val referencia = db.collection("EmpresaDesarroladora").document(empresa.id!!)
                                .collection("Videojuego")
                            referencia
                                .add(nuevoVideojuego)
                                .addOnSuccessListener {
                                    abrirActividadConParametros(ListaVideojuegos::class.java,empresa!!)

                                }.addOnFailureListener {}


                        }


                    }

                )
                builder.setNegativeButton(
                    "No",
                    null
                )
                val dialog = builder.create()
                dialog.show()
                return@setOnClickListener

            }

            else{
                msg.setText("Datos inválidos")

            }
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

}