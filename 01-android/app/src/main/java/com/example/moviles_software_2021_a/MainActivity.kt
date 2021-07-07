package com.example.moviles_software_2021_a

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO=401
    val CODIGO_RESPUESTA_INTENT_IMLICITO=402
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonIrCicloVida= findViewById<Button>(
            R.id.btn_ir_ciclo_vida
        )
        botonIrCicloVida.setOnClickListener{
            abrirCicloVida(
                ACicloVida::class.java
            )

        }
        val botoIrListView=findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botoIrListView.setOnClickListener{
            abrirCicloVida(
                BListView::class.java
            )
        }

        val botonIntentExplicito=findViewById<Button>(
            R.id.btn_ir_intent
        )
        botonIntentExplicito.setOnClickListener{
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }

        val botonAbrirIntentImplicito=findViewById<Button>(
            R.id.btn_intent_implicito
        )
        botonAbrirIntentImplicito
            .setOnClickListener {
                val intentConRespuestaImplicio=Intent(
                    Intent.ACTION_PICK, //Accion
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI//Solicitamos URI
                )
                startActivityForResult(intentConRespuestaImplicio,CODIGO_RESPUESTA_INTENT_IMLICITO)


            }




    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(this,clase)
        intentExplicito.putExtra("nombre","Santiago")
        intentExplicito.putExtra("apellido","Villegas")
        intentExplicito.putExtra("edad",21)
        intentExplicito.putExtra("entrenador",BEntrenador("Santiago","Villegas"))
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODIGO_RESPUESTA_INTENT_EXPLICITO ->{
                if(resultCode== RESULT_OK){
                    Log.i("intent-explicito","Se actualizo los datos")
                    if(data !=null){
                        val nombre=data.getStringExtra("nombreModificado")
                        val edad=data.getIntExtra("edadModificada",0)
                        Log.i("intent-explicito","${nombre}")
                        Log.i("intent-explicito","${edad}")
                    }
                }
            }
            CODIGO_RESPUESTA_INTENT_IMLICITO->{
                if(resultCode== RESULT_OK){
                    if(data !=null){
                        val uri:Uri = data.data!!
                        val cursor=contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                        cursor?.moveToFirst()
                        val indiceRelefono=cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono= cursor?.getString(
                            indiceRelefono!!
                        )
                        cursor?.close()
                        Log.i("resultado","Telefono ${telefono}")
                    }
                }

            }
        }
    }

    fun abrirCicloVida(
        clase: Class<*>
    ){

        val intentExplicito=Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }
}