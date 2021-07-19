package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat

class EditarVideojuego : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    val instanciaVideojuegiBDD=ESqliteHelperVideojuego(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_videojuego)
        val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")
        val videojuego=intent.getParcelableExtra<Videojuego>("Juego")

        val txtNombre=findViewById<EditText>(
            R.id.editText_nombreVideojuegoE
        )
        val txtRecaudacion=findViewById<EditText>(
            R.id.editText_recaudacionE
        )
        val txtFecha=findViewById<EditText>(
            R.id.editText_FechaSalidaE
        )
        val txtGenero=findViewById<EditText>(
            R.id.editText_generoE
        )
        val txtMultijugador=findViewById<EditText>(
            R.id.editText_multijugadorE
        )
        val msg=findViewById<TextView>(
            R.id.textView_msgEditarVideojuego
        )

        val botoEditarVideojuego=findViewById<Button>(
            R.id.btn_editarVideojuego
        )
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        if (videojuego != null) {
            txtNombre.setText(videojuego.nombre)
            txtRecaudacion.setText(String.format("%.2f", videojuego.recaudacion).toString())
            txtFecha.setText(sdf.format((videojuego.fechaSalida)) )
            txtGenero.setText(videojuego.generoPrincipal)
            txtMultijugador.setText(videojuego.multijugador.toString())
        }

        botoEditarVideojuego.setOnClickListener{
            if(Verificadores.Companion.validadorStrings(txtNombre.getText().toString())&&
                Verificadores.Companion.validadorSaldo(txtRecaudacion.getText().toString())&&
                Verificadores.Companion.validadorStrings(txtGenero.getText().toString())&&
                Verificadores.Companion.validadorBoleano(txtMultijugador.getText().toString())&&
                Verificadores.Companion.validadorFecha(txtFecha.getText().toString())
            ) {
                msg.setText("")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                builder.setMessage("¿Está seguro de que desea editar este Videojuego?")


                builder.setPositiveButton(
                    "Si",
                    { dialog, which ->
                        if (empresa != null) {
                            if (videojuego != null) {
                                instanciaVideojuegiBDD.actualizarVideojuegoFormulario(
                                    txtNombre.getText().toString(),
                                    (txtRecaudacion.getText().toString().toDouble()),
                                    txtFecha.getText().toString(),
                                    txtGenero.getText().toString(),
                                    txtMultijugador.getText().toString(),
                                    videojuego.empresaId!!,
                                    videojuego.id!!
                                    )
                            }
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