package es.proyecto.androidmaster.model
import es.proyecto.androidmaster.model.Jugador

class Equipo(private val nombre:String, private var encuentrosJugados:Int, private var encuentrosGanados:Int, private var encuentrosPerdidos:Int, private var partidosFavor:Int, private var partidosContra:Int) {

    private var encuentrosEmpatados:Int = encuentrosJugados - encuentrosGanados - encuentrosPerdidos
    private var puntos:Int = encuentrosGanados * 2 + encuentrosEmpatados
    private lateinit var jugadores:MutableList<Jugador>




     fun getEncuentrosEmpatados(): Int{
        return encuentrosEmpatados
    }

    fun getEncuentrosJugados(): Int{
        return encuentrosJugados
    }
    fun getEncuentrosPerdidos(): Int{
        return encuentrosPerdidos
    }
    fun getEncuentrosGanados(): Int{
        return encuentrosGanados
    }
    fun getPartidosFavor():Int {
        return partidosFavor
    }
    fun getPartidosContra():Int {
        return partidosContra
    }

     fun getPuntos():Int{
        return puntos
    }
    fun getValores(index:Int):String{
        var valor:String = ""
        when(index){
            0 -> valor = nombre
            1 -> valor = encuentrosJugados.toString()
            2 -> valor = encuentrosGanados.toString()
            3 -> valor = encuentrosPerdidos.toString()
            4 -> valor = partidosFavor.toString()
            5 -> valor = partidosContra.toString()
            6 -> valor = puntos.toString()
        }
        return valor
    }

    fun getNombre() = nombre
}