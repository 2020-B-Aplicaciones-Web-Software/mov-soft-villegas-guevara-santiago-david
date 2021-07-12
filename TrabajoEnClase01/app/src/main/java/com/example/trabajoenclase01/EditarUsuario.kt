package com.example.trabajoenclase01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText

class EditarUsuario : AppCompatActivity() {

    val instanciaBDD=ESqliteHelperUsuario(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)
        val usuario=intent.getParcelableExtra<EUsuarioBBD>("Usuario")

        val txtNombre=findViewById<EditText>(
            R.id.editTextUpdate_Nombre
        )


        val txtDescripcion=findViewById<EditText>(
            R.id.editTextUpdate_Descripcion
        )
        val name:String?= usuario?.nombre
        txtNombre.setText(name)


        val description:String?= usuario?.descripcion
        txtDescripcion.setText(description)


        val botoAgregarUsuario=findViewById<Button>(
            R.id.btn_agregar
        )
        botoAgregarUsuario.setOnClickListener{
            if (usuario != null) {
                instanciaBDD.actualizarUsuarioFormulario(
                    txtNombre.getText().toString(),
                    txtDescripcion.getText().toString(),
                    usuario.id)
            }
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