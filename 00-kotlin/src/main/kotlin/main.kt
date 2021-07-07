fun main() {
    println("Hola mundo")
    // JAVA Int edad=12;
   //Duck Typing
    var edadProfesor = 31
    //var edadProfesor: Int = 31
    var sueldoProfesor=12.1
    //var sueldoProfesor: Double=12.1
    // edadProfesor=12.1
    // sueldoProfesor=1

    //MUTABLES( Re asignar)/ INMUTABLES(No Re asignar)
   //MUTABLES
    var edadCachoro: Int=0
    edadCachoro=1
    edadCachoro=2
    edadCachoro=3
    //INMUTABLES
    val numeroCedula=1804356812
   // numeroCedula=122

    //var cualquierCosa: Any=0
    //cualquierCosa=Date()

    //Tipos de variables (JAVA) -> Primitivos
    //Int Double Float Boolean Char, Date
    val nombreProfeso: String="Adrian Eguez"
    val sueldo : Double=12.2
    val estadoCivil: Char='S'
    val casado: Boolean=false

    //Condicionales
    if(true){

    }else{

    }

    when(estadoCivil){
        ('C') ->{
            println("Huir")
        }
        ('S') ->{
            println("Conversar")
        }
        'N' ->{
            println("Nada")
        }
        'P'-> println("Profesor")
        else ->{
            println("No tiene estado civil")
        }

    }
    imprimirNombre("Santiago")
    val sueldoMayorEstablecido= if (sueldo>12.2) 500.9 else 0
    calcularSueldo(60.13)

    calcularSueldo(
        bono= 3.00,
        tasa=12.00,
    sueldo=100.2

    )



    //Arreglo estatico

    //val arregloEstatico:

    val arregloEstatico: Array <Int> = arrayOf(1,2,3,4)
    //Arreglo dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12)


    //Operadores, ya no vamos usar el for o el while

    //FOR EACH->Unit
    //Iterar un arreglo
    val respuestaForEach: Unit= arregloDinamico
        .forEach{valorActual:Int->
            println("Valor actual  ${valorActual}")
        }

   arregloDinamico
        .forEach{  it: Int->
            //it ->parametroo por defecto
            println("Valor actual  ${it}")
        }

    arregloDinamico.forEach{println("Valor actual  ${it}")}

    //FOR EACH -> Indexado
    arregloDinamico
        .forEachIndexed{indice:Int, valorActual:Int->
            println("Valor actual  ${valorActual}, Indice actual: ${indice}")
        }
    //Map -> Muta el arreglo (cambia el arreglo)
    //Mao -> List<...>
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve en un nuero Arreglo con los valores modificados
    val respuestaMap: List<Double> = arregloDinamico
        .map{ valorActual: Int ->
            return@map valorActual.toDouble()+100.00
        }
    println(respuestaMap)

    //Filter -> FILTRAR EL ARREGLO
    //FILTER-. devuelve un arreglo filtrado
    //1) Devolver una expresion (TRUE O FALSE)
    //2) Nuevo arreglo filtrado


    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int->
            val mayoreslACinco: Boolean = valorActual >5
            return@filter mayoreslACinco
        }
    val respuestaFilter2: List<Int> = arregloDinamico
        .filter { valorActual: Int->
            val menorIgualACinco: Boolean = valorActual <=5
            return@filter menorIgualACinco
        }
    //OR AND
    //OR -> ANY (Alguno cumple?)
    val respuestaAny: Boolean=arregloDinamico
        .any{ valorActual: Int ->
            return@any (valorActual>5)
        }
    println(respuestaAny)


    //and -> ALL(Todos cumplen?)
    val respuestaAll: Boolean=arregloDinamico
        .all{ valorActual: Int ->
            return@all (valorActual>5)
        }
    println(respuestaAll)

    //REDUCE -> Valor acumulado
    //Valor acumulado=0 (siempre 0 en lenguaje Kotlin)
    // [1,2,3,4,5]-> Sume todos los valor del arreglo
    //valorIteracion1= valorEmpieza+1=0+1=1 -> Iteracion 1
    //valorIteracion2= valorIteracion1=1+2=3 -> Iteracion 2
    //valorIteracion3= valorIteracion2=3+3=6 -> Iteracion 3
    //valorIteracion4= valorIteracion3=6+4=10 -> Iteracion 4
    //valorIteracion5= valorIteracion4=10+5=15 -> Iteracion 5
    val respuestReduce: Int=arregloDinamico
        .reduce{//acumulado =0 -> Siempre empieza en 0
            acumulado: Int, valorActual: Int->

            return@reduce(acumulado + valorActual)

        }
    println(respuestReduce)

    //100
    //
    val arregloDanio= arrayListOf<Int>(12,15,8,10)
    val respuestaReduceFold=arregloDanio


        .fold(100, //acumulado inicial
            { acumulado,valorActualIteracion->
                return@fold acumulado-valorActualIteracion
            }
        )
    println(respuestaReduceFold)

    val vidaActual: Double=arregloDinamico
        .map{it*2.3}//arreglo
        .filter{it>20}//arreglo
        .fold(100.00,{acc, i->acc-i})
        .also { println(it) }
    println("Valor vida actual ${vidaActual}")

    val ejemploUno= Suma(1,2)
    val ejemploDos= Suma(null,2)
    val ejemploTres= Suma(1,null)
    val ejemploCuatro= Suma(null,null)

    println(ejemploUno.sumar())
    println(ejemploDos.sumar())
    println(ejemploTres.sumar())
    println(ejemploCuatro.sumar())
} //FIN bloque MAIN



//Unit en lugar de void
fun imprimirNombre(nombre: String): Unit{
    println("Nombre ${nombre}")
}
fun calcularSueldo(
    sueldo: Double,
    tasa:Double=12.00,
    bono: Double? = null
):Double{
    if(bono!=null) {
        println("${sueldo * (100 / tasa)+bono}")
        return sueldo * (100 / tasa)+bono
    }else{
        return sueldo * 100 / tasa
    }
}

abstract  class NumeroJava{
    protected val numeroUno: Int //Propiiedad clase
    private val numeroDos: Int //Propiedad clase
    constructor(
        uno: Int,
        dos: Int,

    ){
        //this.numeroUno=uno
        //this.numeroDos=dos
        numeroUno=uno
        numeroDos=dos
        println("Inicializar")

    }

    //instancia.numeroUno
    //instancia.numeroDos


}
abstract class Numeros(//Contructor Primario
    protected val numeroUno: Int, //Propiiedad clase
    protected val numeroDos: Int, //Propiedad clase
){
    init {// Bloque inicio del contructor primario
        println("Inicializar")
    }
}

class Suma(//Constructor primario
    uno: Int, //Parametro requerido
    dos: Int, //Parametro requerido

): Numeros(// Contructor "papa" (super)
    uno,
    dos
){
    init {
        this.numeroUno
        this.numeroDos

    }
    constructor(//segundo constructor
        uno: Int?,
        dos: Int
   ):this(//llamada contructor primario
    if(uno==null) 0 else uno,
        dos

   )

    constructor(//tercer constructor
        uno: Int,
        dos: Int?
    ):this(//llamada contructor primario

        uno,
        if(dos==null) 0 else dos

    )

    constructor(//cuarto constructor
        uno: Int?,
        dos: Int?
    ):this(//llamada contructor primario

        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos

    )

    //public fun sumar():Int{
    fun sumar():Int{
        //val total: Int = this.numeroUno+this.numeroDos
        val total: Int = numeroUno+numeroDos
        agregarHistorial(total)
        return total
    }

    // SINGLETON
    companion object{
        val historialSumas= arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
            println(historialSumas)
        }
    }
}



