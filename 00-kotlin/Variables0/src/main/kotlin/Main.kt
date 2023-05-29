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


