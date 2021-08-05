import java.util.regex.Matcher
import java.util.regex.Pattern

class Verificadores {
    companion object {

        fun validadorStrings(cadena: String?): Boolean {
            var resultado = false
            val p = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ][:space:]{3,20}")
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

