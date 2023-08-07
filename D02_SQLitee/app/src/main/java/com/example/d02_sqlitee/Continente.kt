package com.example.d02_sqlitee

import java.util.*
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat

//Parceable para enviar y recibir entre componentes, facilita transferencia y persistencia
class Continente(
    var id:Int,
    var nombre: String,
    var esHemisferioNorte: Boolean,
    var fechaCivilizacion: Date,
    var cantidadPaises: Int,
    var area: Double
) : Parcelable {  //la clase es capaz de ser "parcelada" o "desparcelada". La serialización y deserialización de objetos se utiliza para transferir objetos entre componentes de la aplicación o para almacenar objetos
    override fun toString(): String {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val fechaString = dateFormat.format(fechaCivilizacion)
        return "${nombre} - ${esHemisferioNorte} - $fechaString a.C - ${cantidadPaises} países - ${area} km2"
    }

    fun crearContinente(nombre: String, esHemisferioNorte: Boolean, fechaPrimeraCiv: Date,cantidadPaises: Int, area: Double) {
        this.nombre = nombre
        this.esHemisferioNorte = esHemisferioNorte
        this.fechaCivilizacion = fechaPrimeraCiv
        this.cantidadPaises = cantidadPaises
        this.area = area
    }

    constructor(parcel: Parcel) : this( //Un Parcel es un contenedor de datos que se utiliza para enviar y recibir objetos
        parcel.readInt(),               //Este constructor se utiliza para deserializar un objeto Continente a partir de un Parcel
        parcel.readString()!!,          //( se leen los valores de las propiedades del Parcel y se asignan a las propiedades correspondientes de la clase)
        parcel.readByte() != 0.toByte(),
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) { //para escribir los valores de las propiedades de la clase en un Parcel
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeByte(if (esHemisferioNorte) 1 else 0)
        parcel.writeLong(fechaCivilizacion.time)
        parcel.writeInt(cantidadPaises)
        parcel.writeDouble(area)
    }

    override fun describeContents(): Int { //devuelve un valor entero que describe el tipo de objetos especiales contenidos en el Parcel.
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Continente> { //para crear instancias de la clase Continente a partir de un Parcel
        override fun createFromParcel(parcel: Parcel): Continente {
            return Continente(parcel)
        }

        override fun newArray(size: Int): Array<Continente?> {
            return arrayOfNulls(size)
        }
    }
}
