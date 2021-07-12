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
                    BEntrenador("Santy","s@s.com",null)
            )
            arregloBEntrenador
                .add(
                    BEntrenador("Carlos","c@c.com",null)
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Kevin","k@k.com",null)
                )
        }
    }
}
