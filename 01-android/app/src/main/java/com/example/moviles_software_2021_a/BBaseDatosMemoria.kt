package com.example.moviles_software_2021_a

class BBaseDatosMemoria {
    companion object{
        //propiedades.
        //mmetodos
        //estaticos
        val arregloBEntrenador= arrayListOf<BEntrenador>()
        init{
            arregloBEntrenador
                .add(
                    BEntrenador("Santy","s@s.com")
            )
            arregloBEntrenador
                .add(
                    BEntrenador("Carlos","c@c.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Kevin","k@k.com")
                )
        }
    }
}
