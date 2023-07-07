import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Pais(
    val nombre: String,
    val esCapital: Boolean,
    val fechaIndependencia: Date,
    var poblacion: Int,
    val indiceDH: Double
){
    companion object {  //para no crear instancia
        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        fun guardarPaises(paises: List<Pais>, archivo: String) {
            val file = File(archivo)
            file.bufferedWriter().use { writer ->
                paises.forEach { pais ->
                    writer.write("${pais.nombre}|")
                    writer.write("${pais.esCapital}|")
                    writer.write("${dateFormat.format(pais.fechaIndependencia)}|")
                    writer.write("${pais.poblacion}|")
                    writer.write("${pais.indiceDH}\n")

                }
            }
        }

        fun cargarPaises(archivo: String): List<Pais> {
            val file = File(archivo)
            val paises = mutableListOf<Pais>()

            file.bufferedReader().use { reader ->
                reader.lines().forEach { line ->
                    val campos = line.split("|")
                    if (campos.size == 5) {
                        val nombre = campos[0]
                        val esCapital = campos[1].toBoolean()
                        val fechaIndependencia = dateFormat.parse(campos[2])
                        val poblacion = campos[3].toInt()
                        val idh = campos[4].toDouble()


                        val pais = Pais(
                            nombre,
                            esCapital,
                            fechaIndependencia,
                            poblacion,
                            idh
                        )
                        paises.add(pais)
                    }
                }
            }

            return paises
        }
    }

    fun leerPaises(paises: List<Pais>) {
        if (paises.isEmpty()) {
            println("No hay paises registrados")
        } else {
            println("Países registrados:")
            paises.forEach { pais: Pais ->
                println("- ${pais.nombre}")
                println("  Es Capital: ${pais.esCapital}")
                println("  Fecha de Independencia: ${dateFormat.format(pais.fechaIndependencia)}")
                println("  Población: ${pais.poblacion}")
                println("  IDH: ${pais.indiceDH}")
            }
        }
    }

    fun ingresarPais(paises: MutableList<Pais>) {
        println("Ingrese el nombre del pais:")
        val nombre = readLine() ?: ""

        println("¿Es Capital? (true/false):")
        val esCapitalInput = readLine() ?: ""
        val esCapital = esCapitalInput.toBoolean()

        println("Ingrese la fecha de independencia (dd/MM/yyyy):")
        val fechaIndependenciaInput = readLine() ?: ""
        val fechaIndependencia = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaIndependenciaInput)

        println("Ingrese la población:")
        val poblacionInput = readLine() ?: ""
        val poblacion = poblacionInput.toIntOrNull()

        println("Ingrese el IDH:")
        val IDHInput = readLine() ?: ""
        val IDH = IDHInput.toDoubleOrNull()

        if (nombre.isNotEmpty() && poblacion != null && IDH != null && fechaIndependencia != null) {
            val pais = Pais(nombre, esCapital, fechaIndependencia, poblacion, IDH)
            paises.add(pais)
            println("País agregado correctamente.")
        } else {
            println("Los datos ingresados no son válidos.")
        }
    }


    fun actualizarPais(paises: MutableList<Pais>){
        println("Ingrese el nombre del país a actualizar :")
        val nombre = readLine() ?: ""

        val pais = paises.find { it.nombre.equals(nombre, ignoreCase = true) }
        if (pais != null) {
            println("Ingrese la nueva población del país:")
            val pInput = readLine() ?: ""
            val p = pInput.toIntOrNull()

            if (p != null) {
                pais.poblacion = p
                println("Pais actualizado correctamente.")
            } else {
                println("población ingresada no es válida.")
            }
        } else {
            println("El País no existe.")
        }
    }

    fun eliminarPais(paises: MutableList<Pais>) {
        println("Ingrese el nombre del país a eliminar:")
        val nombre = readLine() ?: ""

        val pais = paises.find { it.nombre.equals(nombre, ignoreCase = true) }
        if (pais != null) {
            paises.remove(pais)
            println("País eliminado correctamente.")
        } else {
            println("El país no existe.")
        }
    }
}
