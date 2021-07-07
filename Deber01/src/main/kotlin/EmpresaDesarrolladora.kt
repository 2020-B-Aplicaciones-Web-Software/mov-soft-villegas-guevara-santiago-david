import com.google.gson.Gson
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class EmpresaDesarrolladora(
    var nombre: String,
    var numeroTrabajadores: Int,
    var fechaFundacion: Date,
    var pais: String,
    var independiente: Boolean,
    val videojuegos: ArrayList<Videojuego> = arrayListOf<Videojuego>()


){
    fun actualizar(nombre: String,numeroTrabajadores: Int,fechaFundacion: Date,pais: String,independiente: Boolean){
        this.nombre=nombre
        this.numeroTrabajadores=numeroTrabajadores
        this.fechaFundacion=fechaFundacion
        this.pais=pais
        this.independiente=independiente
        BaseDeDatosMemoria.actualizarJson()
    }
    fun agregarVideojuego(videojuego:Videojuego){
        videojuegos.add(videojuego)
        BaseDeDatosMemoria.actualizarJson()
    }

    fun eliminarVideojuego(indice: Int){
        videojuegos.removeAt(indice)
        BaseDeDatosMemoria.actualizarJson()
    }



    override fun toString(): String {
        return ("${nombre}-${fechaFundacion}-${videojuegos}")
    }

}
