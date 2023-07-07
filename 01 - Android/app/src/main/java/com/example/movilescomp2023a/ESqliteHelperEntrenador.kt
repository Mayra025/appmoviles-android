package com.example.movilescomp2023a

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class ESqliteHelperEntrenador (
    contexto: Context?, //THIS   importado
):SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador=
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                 nombre VARCHAR(50),
                 descripcion VARCHAR(50)
                 )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun crearEntrenador(
        nombre:String,
        descripcion:String
    ):Boolean{
        val basedatosEscritura=writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar=basedatosEscritura
            .insert(
                "ENTRENADOR", //nombre tabla
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt()==-1)false else true

    }

    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura=writableDatabase

        //where ID=?
        val parametrosConsultaDelete= arrayOf(id.toString())
        val resultadoEliminacion=conexionEscritura
            .delete(
                "ENTRENADOR", //nombre tabla
                "id=?", //consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt()==-1)false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre:String,
        descripcion:String,
        id:Int
    ):Boolean{
        val conexionEscritura=writableDatabase
        val valoresAActualizar= ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion", descripcion)

        //WHERE id=?
        val parametrosConsultaActualizar= arrayOf(id.toString())
        val resultadoActualizacion=conexionEscritura
            .update(
                "ENTRENADOR", //nombre tabla
                valoresAActualizar,
                "id=?", //consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt()==-1)false else true

    }

    fun consultarEntrenadorPorID(id:Int):BEntrenador{
        val baseDatosLectura=readableDatabase
        val scriptConsultaLectura="""
            SELECT* FROM ENTRENADOR WHERE ID=?
            """.trimIndent()
        val parametrosConsultaLectura= arrayOf(id.toString())
        val resultadoConsultaLectura=baseDatosLectura.rawQuery( //CONSULTA ...
            scriptConsultaLectura,
            parametrosConsultaLectura,
            )

        //logica búsqueda
        val existeUsuario=resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado=BEntrenador(0,"","")
        val arreglo= arrayListOf<BEntrenador>() //si tuvieramos arreglo
        if (existeUsuario){
            do{
                val id=resultadoConsultaLectura.getInt(0) //indice
                val nombre=resultadoConsultaLectura.getString(1)
                val descripcion=resultadoConsultaLectura.getString(2)
                if (id!=null) {
                    //lleanr arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return usuarioEncontrado
    }

   

}