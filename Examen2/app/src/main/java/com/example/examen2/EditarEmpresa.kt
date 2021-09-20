package com.example.examen2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class EditarEmpresa : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empresa)
        this.setTitle("Editar empresa")
        val empresa=intent.getParcelableExtra<EmpresaDesarrolladora>("Empresa")

        val txtNombre=findViewById<EditText>(
            R.id.editText_nombreEmpresaE
        )
        val txtTrabajadores=findViewById<EditText>(
            R.id.editText_numTrabajadoresE
        )
        val txtFecha=findViewById<EditText>(
            R.id.editText_FechaFundacionE
        )
        val txtPais=findViewById<EditText>(
            R.id.editText_paisE
        )
        val txtIndependiente=findViewById<EditText>(
            R.id.editText_independienteE
        )
        val msg=findViewById<TextView>(
            R.id.textView_msgEditarEmpresa
        )

        val botoAgregarUsuario=findViewById<Button>(
            R.id.btn_editarEmpresa
        )



        if (empresa != null) {
            txtNombre.setText(empresa.nombre)
            txtTrabajadores.setText(empresa.numeroTrabajadores.toString())
            txtFecha.setText(empresa.fechaFundacion )
            txtPais.setText(empresa.pais)
            txtIndependiente.setText(empresa.independiente.toString())
        }





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
                builder.setMessage("¿Está seguro de que desea editar esta Empresa Desarrolladora?")


                builder.setPositiveButton(
                    "Si",
                    { dialog, which ->
                        if (empresa != null) {
                            val db = Firebase.firestore
                            val referenciaEmpresa=db.collection("EmpresaDesarroladora")
                                .document(empresa.id!!)

                            db.runTransaction { transaction ->
                                transaction.update(referenciaEmpresa, mapOf(
                                    "nombre" to txtNombre.getText().toString(),
                                    "numeroTrabajadores" to txtTrabajadores.getText().toString().toInt(),
                                    "fechaFundacion" to txtFecha.getText().toString(),
                                    "pais" to txtPais.getText().toString(),
                                    "independiente" to txtIndependiente.getText().toString().toBoolean()

                                ))


                            }
                        }
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