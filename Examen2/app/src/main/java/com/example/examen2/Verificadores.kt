package com.example.examen2

import java.util.regex.Matcher
import java.util.regex.Pattern

class Verificadores {
    companion object {
        fun validadorSaldo(cadena: String?): Boolean {
            var resultado = false
            val p: Pattern = Pattern.compile("[1-9]{1}[0-20]*[.][0-9]{2}")
            val validar: Matcher = p.matcher(cadena)
            if (validar.matches()) {
                resultado = true
            }
            return resultado
        }
        fun validarLongitud(number:String):Boolean{

            try {
                val numero:Double=number.toDouble()
                if(numero>=-180 && numero<=180){
                    return true
                }

            } catch (nfe: NumberFormatException) {
                return false

            }
            return false
        }

        fun validarLatitud(number:String):Boolean{
            try {
                val numero:Double=number.toDouble()
                if(numero>=-90 && numero<=90){
                    return true
                }

            } catch (nfe: NumberFormatException) {
                return false

            }
            return false
        }

        fun validadorStrings(cadena: String?): Boolean {
            var resultado = false
            val p = Pattern.compile("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ]{3,20}")
            val validar = p.matcher(cadena)
            if (validar.matches()) {
                resultado = true
            }
            return resultado
        }


        fun isNumeric(cadena: String): Boolean {
            return try {
                cadena.toInt()
                true
            } catch (nfe: NumberFormatException) {
                false
            }
        }

        fun validadorBoleano(cadena: String?): Boolean {
            return "true".equals(cadena) || "false".equals(cadena)
        }
        fun validadorFecha(cadena: String?): Boolean {
            var resultado = false
            val p = Pattern.compile("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")
            val validar = p.matcher(cadena)
            if (validar.matches()) {
                resultado = true
            }
            return resultado
        }

    }
}