import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class BaseDeDatosMemoria {
    companion object {
        val ruta="src/main/resources/empresaJuego.json"
        var arregloEmpresaDesarrolladora = arrayListOf<EmpresaDesarrolladora>()
        init{
            try {

                val gson = Gson()
                val reader: Reader = Files.newBufferedReader(Paths.get(ruta))
                arregloEmpresaDesarrolladora = Gson().fromJson(reader, object : TypeToken<List<EmpresaDesarrolladora?>?>() {}.type)

                reader.close()
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
        fun actualizarJson(){
            try {
                val gson = Gson()
                val writer: Writer = Files.newBufferedWriter(Paths.get(ruta))
                gson.toJson(arregloEmpresaDesarrolladora, writer);
                writer.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
        fun eliminarEmpresa(indice:Int){
            arregloEmpresaDesarrolladora.removeAt(indice)
            actualizarJson()
        }
        fun agregarEmpresa(empresa:EmpresaDesarrolladora){
            arregloEmpresaDesarrolladora.add(empresa)
            actualizarJson()
        }
    }
}