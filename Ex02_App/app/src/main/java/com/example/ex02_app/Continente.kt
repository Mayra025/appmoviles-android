package com.example.ex02_app

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

//Parceable para enviar y recibir entre componentes, facilita transferencia y persistencia
class Continente(
    public var id: String?,

    public var nombre: String?,
    public var esHemisferioNorte: Boolean?,
    public var fechaCivilizacion: Timestamp?,
    public var cantidadPaises: Long?,
    public var area: Long?
) : Parcelable {  //la clase es capaz de ser "parcelada" o "desparcelada". La serialización y deserialización de objetos se utiliza para transferir objetos entre componentes de la aplicación o para almacenar objetos

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun toString(): String {
        return "${nombre} - HN:${esHemisferioNorte} - ${fechaCivilizacion?.toDate()} - ${cantidadPaises} países - ${area} km2"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeValue(esHemisferioNorte)
        parcel.writeParcelable(fechaCivilizacion, flags)
        parcel.writeValue(cantidadPaises)
        parcel.writeValue(area)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Continente> {
        override fun createFromParcel(parcel: Parcel): Continente {
            return Continente(parcel)
        }

        override fun newArray(size: Int): Array<Continente?> {
            return arrayOfNulls(size)
        }
    }
}

