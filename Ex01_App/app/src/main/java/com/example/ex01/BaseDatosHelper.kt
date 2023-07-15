package com.example.ex01
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class BaseDatosHelper (context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "examen"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear las tablas necesarias en la base de datos
        val createContinentesTableQuery = """CREATE TABLE IF NOT EXISTS CONTINENTE
                (idC INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreC TEXT, 
                esHemisferio INTEGER,
                fechaCiv TEXT,
                cantPaises INTEGER,
                area REAL)""".trimIndent()

        val createPaisesTableQuery = """CREATE TABLE IF NOT EXISTS PAIS 
                (idP INTEGER PRIMARY KEY, 
                nombreP TEXT, 
                esCapital INTEGER,
                fechaInd TEXT,
                poblacion INTEGER,
                idh REAL,
                continenteId INTEGER)""".trimIndent()

        db?.execSQL(createContinentesTableQuery)
        db?.execSQL(createPaisesTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    // Métodos para la tabla de continentes

    fun insertContinente(continente: Continente): Long {
        val db = writableDatabase

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaCivilizacion = dateFormat.format(continente.fechaCivilizacion)

        val values = ContentValues().apply {
            put("nombreC", continente.nombre)
            put("esHemisferio", if (continente.esHemisferioNorte) 1 else 0)
            put("fechaCiv", fechaCivilizacion)
            put("cantPaises", continente.cantidadPaises)
            put("area", continente.area)
        }

        val id = db.insert("CONTINENTE", null, values)
        db.close()

        return id
    }

    fun updateContinente(continente: Continente): Int {
        val db = writableDatabase

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaCivilizacion = dateFormat.format(continente.fechaCivilizacion)

        val values = ContentValues().apply {
            put("nombreC", continente.nombre)
            put("esHemisferio", if (continente.esHemisferioNorte) 1 else 0)
            put("fechaCiv", fechaCivilizacion)
            put("cantPaises", continente.cantidadPaises)
            put("area", continente.area)
        }

        val rowsAffected = db.update("CONTINENTE", values, "idC = ?", arrayOf(continente.id.toString()))
        db.close()

        return rowsAffected
    }

    fun deleteContinente(continenteId: Int): Int {
        val db = writableDatabase

        val rowsAffected = db.delete("CONTINENTE", "idC = ?", arrayOf(continenteId.toString()))
        db.close()

        return rowsAffected
    }


    @SuppressLint("Range")
    fun getAllContinentes(): List<Continente> {
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM CONTINENTE", null)

        val continentes = mutableListOf<Continente>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex("idC"))
                    val nombre = it.getString(it.getColumnIndex("nombreC"))
                    val esHemisferioNorte = it.getInt(it.getColumnIndex("esHemisferio")) == 1
                    val fechaString = it.getString(it.getColumnIndex("fechaCiv"))
                    val fecha = dateFormat.parse(fechaString)
                    val cant=it.getInt(it.getColumnIndex("cantPaises"))
                    val area=it.getDouble(it.getColumnIndex("area"))
                    val continente = Continente(id, nombre, esHemisferioNorte,fecha,cant,area)
                    continentes.add(continente)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        db.close()

        return continentes
    }

    // Métodos para la tabla de países

    fun insertPais(pais: Pais): Long {
        val db = writableDatabase
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaInd = dateFormat.format(pais.fechaIndependencia)

        val values = ContentValues().apply {
            put("nombreP", pais.nombre)
            put("esCapital", if (pais.esCapital) 1 else 0)
            put("fechaInd", fechaInd)
            put("poblacion", pais.poblacion)
            put("idh", pais.indiceDH)
            put("continenteId", pais.continenteId)
        }

        val id = db.insert("PAIS", null, values)
        db.close()

        return id
    }

    fun updatePais(pais: Pais): Int {
        val db = writableDatabase
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaInd = dateFormat.format(pais.fechaIndependencia)

        val values = ContentValues().apply {
            put("nombreP", pais.nombre)
            put("esCapital", if (pais.esCapital) 1 else 0)
            put("fechaInd", fechaInd)
            put("poblacion", pais.poblacion)
            put("idh", pais.indiceDH)
        }


        val rowsAffected = db.update("PAIS", values, "idP = ?", arrayOf(pais.id.toString()))
        db.close()

        return rowsAffected
    }

    fun deletePais(paisId: Int): Int {
        val db = writableDatabase

        val rowsAffected = db.delete("PAIS", "idP = ?", arrayOf(paisId.toString()))
        db.close()

        return rowsAffected
    }

    @SuppressLint("Range")
    fun getPaisesByContinente(cId: Int): List<Pais> {
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM PAIS WHERE continenteId = ?", arrayOf(cId.toString()))
        //registros de la tabla "PAIS" que coincidan con un determinado "continenteId",  se almacena en un objeto Cursor

        val paises = mutableListOf<Pais>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex("idP"))
                    val nombre = it.getString(it.getColumnIndex("nombreP"))
                    val esCapital = it.getInt(it.getColumnIndex("esCapital")) == 1
                    val fechaString = it.getString(it.getColumnIndex("fechaInd"))
                    val fecha = dateFormat.parse(fechaString)
                    val poblacion = it.getInt(it.getColumnIndex("poblacion"))
                    val idh = it.getDouble(it.getColumnIndex("idh"))
                    val contId = it.getInt(it.getColumnIndex("continenteId"))

                    val pais = Pais(id, nombre, esCapital, fecha, poblacion, idh, contId)
                    paises.add(pais)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        db.close()

        return paises
    }
}