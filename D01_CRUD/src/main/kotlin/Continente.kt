import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Continente(
    val nombre: String,
    val esHemisferioNorte: Boolean,
    val fechaFundacion: Date,
    val cantidadPaises: Int,
    var area: Double
){
    companion object {  //para no crear instancia
        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        fun guardarContinentes(continentes: List<Continente>, archivo: String) {
            val file = File(archivo)
            file.bufferedWriter().use { writer ->
                continentes.forEach { continente ->
                    writer.write("${continente.nombre}|")
                    writer.write("${continente.esHemisferioNorte}|")
                    writer.write("${dateFormat.format(continente.fechaFundacion)}|")
                    writer.write("${continente.cantidadPaises}|")
                    writer.write("${continente.area}\n")

                }
            }
        }

        fun cargarContinentes(archivo: String): List<Continente> {
            val file = File(archivo)
            val continentes = mutableListOf<Continente>()

            if (file.exists()) {
                file.bufferedReader().use { reader ->
                    reader.lines().forEach { line ->
                        val campos = line.split("|")
                        if (campos.size == 5) {
                            val nombre = campos[0]
                            val esHemisferioNorte = campos[1].toBoolean()
                            val fechaFundacion = dateFormat.parse(campos[2])
                            val cantidadPaises = campos[3].toInt()
                            val area = campos[4].toDouble()

                            val continente = Continente(
                                nombre,
                                esHemisferioNorte,
                                fechaFundacion,
                                cantidadPaises,
                                area
                            )
                            continentes.add(continente)
                        }
                    }
                }
            }

            return continentes
        }
    }



    fun leerContinentes(continentes: List<Continente>) {
        if (continentes.isEmpty()) {
            println("No hay continetes registrados")
        } else {
            println("Continentes registrados:")
            continentes.forEach { continente: Continente ->
                println("- ${continente.nombre}")
                println("  Es Hemisferio Norte: ${continente.esHemisferioNorte}")
                println("  Fecha de Fundación: ${dateFormat.format(continente.fechaFundacion)}")
                println("  Cantidad de Países: ${continente.cantidadPaises}")
                println("  Área: ${continente.area}")
            }
        }
    }

    fun ingresarContinente(continentes: MutableList<Continente>) {
        println("Ingrese el nombre del continente:")
        val nombre = readLine() ?: ""

        println("¿Es Hemisferio Norte? (true/false):")
        val esHemisferioNorteInput = readLine() ?: ""
        val esHemisferioNorte = esHemisferioNorteInput.toBoolean()

        println("Ingrese la fecha de fundación (dd/MM/yyyy):")
        val fechaFundacionInput = readLine() ?: ""
        val fechaFundacion = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaFundacionInput)

        println("Ingrese la cantidad de países:")
        val cantidadPaisesInput = readLine() ?: ""
        val cantidadPaises = cantidadPaisesInput.toIntOrNull()

        println("Ingrese el área:")
        val areaInput = readLine() ?: ""
        val area = areaInput.toDoubleOrNull()

        if (nombre.isNotEmpty() && cantidadPaises != null && area != null && fechaFundacion != null) {
            val continente = Continente(nombre, esHemisferioNorte, fechaFundacion, cantidadPaises, area)
            continentes.add(continente)
            println("Continente agregado correctamente.")
        } else {
            println("Los datos ingresados no son válidos.")
        }
    }





    fun actualizarContinente(continentes: MutableList<Continente>){
        println("Ingrese el nombre del continente a actualizar :")
        val nombre = readLine() ?: ""

        val continente = continentes.find { it.nombre.equals(nombre, ignoreCase = true) }
        if (continente != null) {
            println("Ingrese la nueva área del continente:")
            val aInput = readLine() ?: ""
            val a = aInput.toDoubleOrNull()

            if (a != null) {
                continente.area = a
                println("Continente actualizado correctamente.")
            } else {
                println("area ingresada no es válida.")
            }
        } else {
            println("El continente no existe.")
        }
    }

    fun eliminarContinente(continentes: MutableList<Continente>) {
        println("Ingrese el nombre del continente a eliminar:")
        val nombre = readLine() ?: ""

        val continente = continentes.find { it.nombre.equals(nombre, ignoreCase = true) }
        if (continente != null) {
            continentes.remove(continente)
            println("Continente eliminado correctamente.")
        } else {
            println("El continente no existe.")
        }
    }
}
