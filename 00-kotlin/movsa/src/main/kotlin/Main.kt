fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    //println("Program arguments: ${args.joinToString()}")

    //Tipos de variables

    //INMUTABLES (No se reasignacion =)
    val inmutable: String="Adrian"
    //inmutable="Vicente";

    //MUTABLES (re asignar )
    var mutable:String="Vicente"
    mutable="Adrian"


    //Duck Typing
    val ejemploVariable="Mayra Alexandra"
    val edadEjemplo:Int=12
    ejemploVariable.trim() // trim para quitar espacios antes y despues

    //Variable primitiva
    val nombreProfesor:String="Adrian Eguez"
    val sueldo:Double=1.2
    val estadoCivil:Char='C'
    val mayorEdad: Boolean=true

    //
    val fechaNacimiento: Date=Date()
}