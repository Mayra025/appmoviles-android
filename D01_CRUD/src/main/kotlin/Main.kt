import java.util.Date

fun main() {
    var opcion: Int?  // puede ser entero o nulo
    val continents = mutableListOf<Continente>()
    val paiss = mutableListOf<Pais>()

    // Cargar los registros existentes al iniciar el programa
    continents.addAll(Continente.cargarContinentes("continentes.txt"))
    paiss.addAll(Pais.cargarPaises("paises.txt"))

    do {
        println("\nPrograma Continente-Países")
        println("Ingrese una opción numérica para continuar:")
        println(" 1. Continente")
        println(" 2. Países")
        println(" 0. Salir")
        opcion = readLine()?.toIntOrNull()


        when (opcion) {
            1 -> {
                var subOpcion: Int?

                do {
                    println("\nIngrese una opción numérica:")
                    println(" 1. Leer continentes")
                    println(" 2. Escribir continente")
                    println(" 3. Eliminar continente")
                    println(" 4. Actualizar continente")
                    println(" 0. Volver al menú principal")
                    subOpcion = readLine()?.toIntOrNull()

                    val continent = Continente("", false, Date(), 0, 0.0)

                    when (subOpcion) {
                        1 -> {
                            continent.leerContinentes(continents)
                        }
                        2 -> {
                            if (continents.size < 5) {
                                continent.ingresarContinente(continents)
                                Continente.guardarContinentes(continents, "continentes.txt")
                            } else {
                                println("No se pueden ingresar más de 5 continentes.")
                            }
                        }
                        3 -> {
                            continent.eliminarContinente(continents)
                            Continente.guardarContinentes(continents, "continentes.txt")
                        }
                        4 -> {
                            continent.actualizarContinente(continents)
                            Continente.guardarContinentes(continents, "continentes.txt")
                        }
                        0 -> println("Volviendo al menú principal...")
                        else -> println("Opción inválida")
                    }
                } while (subOpcion != 0) // Se agrega la condición opcion != null para permitir volver al menú principal
            }
            2 -> {
                var subOpcion: Int?

                do {
                    println("\nIngrese una opción numérica:")
                    println(" 1. Leer países")
                    println(" 2. Escribir país")
                    println(" 3. Eliminar país")
                    println(" 4. Actualizar país")
                    println(" 0. Volver al menú principal")
                    subOpcion = readLine()?.toIntOrNull()

                    val pais = Pais("", false, Date(), 0, 0.0)

                    when (subOpcion) {
                        1 -> {
                            pais.leerPaises(paiss)
                        }
                        2 -> {
                            pais.ingresarPais(paiss)
                            Pais.guardarPaises(paiss, "paises.txt")
                        }
                        3 -> {
                            pais.eliminarPais(paiss)
                            Pais.guardarPaises(paiss, "paises.txt")
                        }
                        4 -> {
                            pais.actualizarPais(paiss)
                            Pais.guardarPaises(paiss, "paises.txt")
                        }
                        0 -> println("Volviendo al menú principal...")
                        else -> println("Opción inválida")
                    }
                } while (subOpcion!=0) // Se agrega la condición opcion != null para permitir volver al menú principal
            }
            0 -> println("\nAdiós!")
            else -> println("Opción inválida")
        }

    } while (opcion != 0 ) // Se agrega la condición opcion != null para permitir volver al menú principal
}
