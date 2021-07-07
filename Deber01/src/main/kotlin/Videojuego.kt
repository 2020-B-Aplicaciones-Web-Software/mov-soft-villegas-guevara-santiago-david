import java.util.*

class Videojuego(
    var nombre:String,
    var recaudacion:Double,
    var fechaSalida: Date,
    var generoPrincipal: String,
    var multijugador: Boolean,

) {
    fun actualizar(nombre: String,recaudacion: Double,fechaSalida: Date,generoPrincipal: String,multijugador: Boolean){
        this.nombre=nombre
        this.recaudacion=recaudacion
        this.fechaSalida=fechaSalida
        this.generoPrincipal=generoPrincipal
        this.multijugador=multijugador
        BaseDeDatosMemoria.actualizarJson()
    }


}