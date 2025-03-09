package es.proyecto.androidmaster.model

class Encuentro(private var local:Equipo, private var visitante:Equipo, private var juegosLocal:Int, private var juegosVisitante:Int, private var partidosLocal: Int, private var partidosVisitante:Int ) {
    private lateinit var ganador:Equipo
    private lateinit var perdedor:Equipo





    fun getLocal():Equipo{
        return local
    }
    fun getVisitante():Equipo{
        return visitante
    }
    fun setGanador(win: Equipo){
        ganador = win
    }
    fun setPerdedor(loser: Equipo){
        perdedor = loser
    }
    fun setParGanador(pg: Int){
        partidosLocal = pg
    }
    fun setParPerdedor(pp: Int){
        partidosVisitante = pp
    }

    fun getJuegosLocal() = juegosLocal
    fun getJuegosVisitante() = juegosVisitante
    fun getPartidosLocal() = partidosLocal
    fun getPartidosVisitante() = partidosVisitante
    fun getGanador() = ganador
    fun getPerdedor() = perdedor
}

