package com.example.ex02_app

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

class Pais(
    public var id: String?,
    public var nombre: String?,
    public var esCapital: Boolean?,
    public var fechaIndependencia: Timestamp?,
    public var poblacion: Long?,
    public var indiceDH: Long?,
    public var continenteId: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun toString(): String {
            return "${nombre} - Cp:${esCapital} - ${fechaIndependencia?.toDate()} - ${poblacion} habit. - ${indiceDH}"
        }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeValue(esCapital)
        parcel.writeParcelable(fechaIndependencia, p1)
        parcel.writeValue(poblacion)
        parcel.writeValue(indiceDH)
        parcel.writeString(continenteId)
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
