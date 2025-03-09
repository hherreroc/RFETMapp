package es.proyecto.androidmaster.model

class Jugador(
    private val numeroLicencia: Int,
    private val nombre: String,
    private var tipoLicencia: String,
    private var categoria: Categorias,
    private var puntos: Int,
    private var nacionalidad: String
) {

    enum class Categorias {
        PRE_M, PRE_F, BEN_M, BEN_F, ALE_M, ALE_F, INF_M, INF_F, CAD_M, CAD_F, JUV_M, JUV_F,
        S21_M, S21_F, SEN_M, SEN_F, V40_M, V40_F, V50_M, V50_F, V60_M, V60_F, V70_M, V70_F,
        V80_M, V80_F, V90_M, V90_F
    }

    fun getNombre():String = nombre
    fun getPuntos():Int = puntos
    fun getLicencia():Int = numeroLicencia

}