package com.example.movilescomp2023a

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador= arrayListOf<BEntrenador>()
        init{
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Adrian","a@q.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Vicente","a@q.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Carolina","a@q.com")
                )
        }
    }
}