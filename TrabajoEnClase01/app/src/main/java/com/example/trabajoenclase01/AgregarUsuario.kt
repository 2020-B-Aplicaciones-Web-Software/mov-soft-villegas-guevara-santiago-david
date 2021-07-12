package com.example.trabajoenclase01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AgregarUsuario : AppCompatActivity() {
    val instanciaBDD=ESqliteHelperUsuario(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuario)
        val txtNombre=findViewById<EditText>(
            R.id.editText_Nombre
        )
        val txtDescripcion=findViewById<EditText>(
            R.id.editText_Descripcion
        )

        val botoAgregarUsuario=findViewById<Button>(
            R.id.btn_agregar
        )
        botoAgregarUsuario.setOnClickListener{
            instanciaBDD.crearUsuarioFormulario(
                txtNombre.getText().toString(),
                txtDescripcion.getText().toString())
            abrirActividad(MainActivity::class.java)


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