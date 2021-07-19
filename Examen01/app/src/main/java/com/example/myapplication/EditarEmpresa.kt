package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class EditarEmpresa : AppCompatActivity() {
    val instanciaEmpresaBDD=ESqliteHelperEmpresaDesarrolladora(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empresa)
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


        val sdf = SimpleDateFormat("dd/MM/yyyy")
        if (empresa != null) {
            txtNombre.setText(empresa.nombre)
            txtTrabajadores.setText(empresa.numeroTrabajadores.toString())
            txtFecha.setText(sdf.format((empresa.fechaFundacion)) )
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
                            instanciaEmpresaBDD.actualizarUsuarioFormulario(
                                txtNombre.getText().toString(),
                                Integer.parseInt(txtTrabajadores.getText().toString()),
                                txtFecha.getText().toString(),
                                txtPais.getText().toString(),
                                txtIndependiente.getText().toString(),
                                empresa.id!!
                            )
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