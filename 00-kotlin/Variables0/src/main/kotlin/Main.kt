import java.util.*

fun main(args: Array<String>) {
        println("Hello World!")

    //Tipos de variables
        //INMUTABLES (No se reasignacion =)
        val inmutable: String="Adrian";
        //inmutable="Vicente";

        //MUTABLES (re asignar )
        var mutable:String="Vicente";
        mutable="Adrian";

        //val>var

        //Duck Typing
        val ejemploVariable="Mayra Alexandra"
        val edadEjemplo:Int=12
        ejemploVariable.trim() // trim para quitar espacios antes y despues

        //Variable primitiva
        val nombreProfesor:String="Adrian Eguez"
        val sueldo:Double=1.2
        val estadoCivil:Char='C'
        val mayorEdad: Boolean=true

        val fechaNacimiento: Date= Date()

        //SWITCH
        val estadoCivilWhen="C"
        when (estadoCivilWhen){
            ("C")->{
                println("Casado")
            }
            "S" ->{
                println("Soltero")
            }
            else->{
                println("No sabemos")
            }
        }
        val coqueteo =if (estadoCivilWhen=="S") "Si" else "No"

        calcularSueldo(10.00, 12.00,20.00)
        calcularSueldo(10.00, bonoEspecial = 20.00)  //Named parameters
        calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //  Parametros nombrados


    val sumaUno=Suma(1,1)
    val sumaDos=Suma(null,1)
    val sumaTres=Suma(1,null)
    val sumaCuatro=Suma(null,null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    //ARREGLOS
    // Tipos de Arreglos

    // Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    // Arreglo Dinámicos son los que pueden modificar el contenido
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    //OPERADORES -> Sirve para los arreglos estativos y dinamicos
//FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int -> //un parametro
            println("Valor actual ${valorActual}")
        }
    arregloDinamico.forEach{println(it)} //valor actual (eso)

    arregloEstatico
        .forEachIndexed { indice:Int,valorActual:Int-> //2 parametrs //todo despuesde la flecha se ejecuta
            println("Valor ${valorActual} Indice ${indice}")
        }
    println(respuestaForEach)


//MAP ->Muta el arreglo(Cambia el arreglo)
    //cambia y modifica el arreglo pero en otro arreglo

    //1.- Envia el nuevo valor de la interacion
    //2.- Nos devuelve un NUEVO ARREGLO CON LOS VALORES MODICADOS

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual . toDouble () + 100.00  //@nombreElemento
        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map {it + 15}


//Filter -> FILTRA EL ARREGLO
    //1.- Devolver una expresion (TRUE O FALSE)
    //2.- Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5// Es La Expresion Condicion
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter {it<=5}
    println(respuestaFilter)
    println(respuestaFilterDos)

//OR AND
    //OR -> ANY (Alguno cumple?)
    //OR -> ALL (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int->
            return@any (valorActual >5)
        }
    println(respuestaAny)//true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int->
            return@all (valorActual >5)
        }
    println(respuestaAll)//false

//REDUCE -> Valor acumulado
    //Valor acumualdo = 0 (Siempre 0 en lenguaje Kotlin)
    //[1, 2, 3, 4, 5] -> Sumeme todos los numeros del arreglo
    //valorIteracion1 = valorEmpieza +1 = 0 + 1 = 1 -> Iteracion 1
    //valorIteracion2 = valorIteracion1 +2 = 1 + 2= 3 -> Iteracion 2
    //valorIteracion3 = valorIteracion2 +3 = 3 + 3= 6 -> Iteracion 3
    //valorIteracion4 = valorIteracion3 +4 = 6 + 4= 10 -> Iteracion 4
    //valorIteracion5 = valorIteracion4 +5 = 10 + 5= 15 -> Iteracion 5

    val respuestaReduce : Int = arregloDinamico
        .reduce {
                acumulado: Int, valorActual: Int ->
            return@reduce(acumulado + valorActual)// -> Logica negocio
        }
    println (respuestaReduce)//78
}


//FUNCIONES
fun imprimirNombre(nombre:String):Unit{   //void -> unit
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo:Double, //requerido
    tasa: Double=12.00, //opcional(defecto)
    bonoEspecial:Double?=null,//opcion null -> nullable
): Double{
    if (bonoEspecial==null){
        return sueldo*(100/tasa)

    }else{
        return sueldo*(100/tasa) +bonoEspecial
    }
}


//CLASES
abstract  class NumerosJava{
    protected val num1:Int
    private val num2:Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.num1=uno
        this.num2=dos
        println("Inicializando")
    }
}

abstract class Numeros(  //Constructor PRIMARIO
    //ejm:
    //uno: Int,               //parametro (sin modificador de acceso)
    //private var uno: Int,   // propiedad Publica clase numeros.uno
    //var uno:Int,            //propiedad de la clase     (por defecto publica)
    //public var uno: Int,
    protected val numUno: Int, //propiedad de la clase Protected numeros.numUno
    protected val numDos: Int //propiedad de la clase Protected numeros.numDos
) {
    //var cedula:string=""              (public por defecto)
    //private valorCalculado: Int=0     (private)
    init {
        this.numUno;this.numDos; //this es opcional
        numUno;numDos; // sin this, es lo mismo
        println("Inicializando")
    }
}

class Suma(  //Constructor Primario
    uno: Int,    //Parametro
    dos: Int
) : Numeros(uno, dos) { //Constructor del padre
    init {  //solo para Bloque constructor primario
        this.numUno;numUno;
        this.numDos;numDos;
    }

    constructor( //2do constructor
        uno: Int?,
        dos: Int
    ) : this(     //llamada al constructor primario (q tiene 2 parametros int)
        if (uno == null) 0 else uno,
        dos
    ) {
        numUno;
    }

    constructor(    //3er contructor
        uno: Int,
        dos: Int?
    ) : this(       //llamada al constructor primario
        uno,
        if (dos == null) 0 else uno
    )  //si no necesitamos el bloque {}, lo omitimos

    constructor(    //4to constructor
        uno: Int?,
        dos: Int?
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

        //Metodo de la instancia
    public fun sumar(): Int { // public por defecto, o private o protected
        val total = numUno + numDos;

        agregarHistorial(total)
        return total
    }

    companion object{     //Atributos y Metodos "Compartidos"
       //entre las instancias
        val pi=3.14
        fun elevarAlCuadrado(num:Int):Int{
           return num*num
        }

        val historialSumas= arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int) {
            historialSumas.add(valorNuevaSuma)
        }
    }
}


