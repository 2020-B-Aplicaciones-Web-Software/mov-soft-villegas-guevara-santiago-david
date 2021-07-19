package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class CrearEmpresa : AppCompatActivity() {


    val instanciaEmpresaBDD=ESqliteHelperEmpresaDesarrolladora(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_empresa)

        val txtNombre=findViewById<EditText>(
            R.id.editText_nombreEmpresa
        )
        val txtTrabajadores=findViewById<EditText>(
            R.id.editText_numTrabajadores
        )
        val txtFecha=findViewById<EditText>(
            R.id.editText_FechaFundacion
        )
        val txtPais=findViewById<EditText>(
            R.id.editText_pais
        )
        val txtIndependiente=findViewById<EditText>(
            R.id.editText_independiente
        )
        val msg=findViewById<TextView>(
            R.id.textView_msgCrearEmpresa
        )

        val botoAgregarUsuario=findViewById<Button>(
            R.id.btn_crearEmpresaC
        )

        botoAgregarUsuario.setOnClickListener{
            if(Verificadores.Companion.validadorStrings(txtNombre.getText().toString())&&
                Verificadores.Companion.isNumeric(txtTrabajadores.getText().toString())&&
                Verificadores.Companion.validadorStrings(txtPais.getText().toString())&&
                Verificadores.Companion.validadorBoleano(txtIndependiente.getText().toString())&&
                Verificadores.Companion.validadorFecha(txtFecha.getText().toString())
            ) {
                msg.setText("")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                builder.setMessage("¿Está seguro de que desea crear esta Empresa Desarrolladora?")


                builder.setPositiveButton(
                    "Si",
                    { dialog, which ->
                        instanciaEmpresaBDD.crearEmpresaFormulario(
                            txtNombre.getText().toString(),
                            Integer.parseInt(txtTrabajadores.getText().toString()),
                            txtFecha.getText().toString(),
                            txtPais.getText().toString(),
                            txtIndependiente.getText().toString()
                        )
                        abrirActividad(MainActivity::class.java)

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