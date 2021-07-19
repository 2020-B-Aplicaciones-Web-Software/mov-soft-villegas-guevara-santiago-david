package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class CrearVideojuego : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    val instanciaVideojuegiBDD=ESqliteHelperVideojuego(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_videojuego)
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
                Verificadores.Companion.validadorFecha(txtFecha.getText().toString())
            ) {
                msg.setText("")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                builder.setMessage("¿Está seguro de que desea crear este Videojuego?")


                builder.setPositiveButton(
                    "Si",
                    { dialog, which ->
                        if (empresa != null) {
                            instanciaVideojuegiBDD.crearVideojuegoFormulario(
                                txtNombre.getText().toString(),
                                (txtRecaudacion.getText().toString().toDouble()),
                                txtFecha.getText().toString(),
                                txtGenero.getText().toString(),
                                txtMultijugador.getText().toString(),
                                empresa.id!!

                            )
                        }
                        abrirActividadConParametros(ListaVideojuegos::class.java,empresa!!)

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