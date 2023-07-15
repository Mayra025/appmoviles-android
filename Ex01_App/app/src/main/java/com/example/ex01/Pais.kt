package com.example.ex01

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Pais(
    var id:Int,
    var nombre: String,
    var esCapital: Boolean,
    var fechaIndependencia: Date,
    var poblacion: Int,
    var indiceDH: Double,
    var continenteId:Int
) : Parcelable {
        override fun toString(): String {
            return "${nombre} - ${esCapital} - ${fechaIndependencia} - ${poblacion} habit. - ${indiceDH}"
        }

        fun crearPais(nombre: String, esCapital: Boolean, fechaIndependencia: Date,poblacion: Int, idh: Double,iC:Int) {
            this.nombre = nombre
            this.esCapital = esCapital
            this.fechaIndependencia = fechaIndependencia
            this.poblacion = poblacion
            this.indiceDH = idh
            this.continenteId=iC
        }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeByte(if (esCapital) 1 else 0)
        parcel.writeLong(fechaIndependencia.time)
        parcel.writeInt(poblacion)
        parcel.writeDouble(indiceDH)
        parcel.writeInt(continenteId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pais> {
        override fun createFromParcel(parcel: Parcel): Pais {
            return Pais(parcel)
        }

        override fun newArray(size: Int): Array<Pais?> {
            return arrayOfNulls(size)
        }
    }
}
