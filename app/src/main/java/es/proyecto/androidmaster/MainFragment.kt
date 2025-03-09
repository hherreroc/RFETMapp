package es.proyecto.androidmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.proyecto.androidmaster.model.Encuentro
import es.proyecto.androidmaster.model.EncuentrosAdapter
import es.proyecto.androidmaster.model.Equipo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainFragment : Fragment() {


    private lateinit var tablaFil1: LinearLayout
    private lateinit var rvEncuentros: RecyclerView
    private lateinit var encuentrosAdapter: EncuentrosAdapter





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializarComponentes(view)
        iniciarVerificacionPeriodica()
    }




    private fun inicializarComponentes(view: View){
        rvEncuentros = view.findViewById(R.id.rvEncuentros)
        tablaFil1 =view.findViewById(R.id.tabla)

        cargarDatos()

    }


    private fun cargarDatos() {
        var db = Firebase.firestore
        var equipos: MutableList<Equipo> = mutableListOf()

        db.collection("EQUIPOS")
            .orderBy("PUNTOS", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {

                val datos = it.documents
                for (equipo in datos) {
                    var nombreEquipo = equipo.getString("NOMBRE")
                    var ej = equipo.getLong("ENCUENTROS_JUGADOS")?.toInt()
                    var eg = equipo.getLong("ENCUENTROS_GANADOS")?.toInt()
                    var ep = equipo.getLong("ENCUENTROS_PERDIDOS")?.toInt()
                    var pf = equipo.getLong("PARTIDOS_FAVOR")?.toInt()
                    var pc = equipo.getLong("PARTIDOS_CONTRA")?.toInt()

                    equipos.add(Equipo(nombreEquipo!!, ej!!, eg!!, ep!!, pf!!, pc!!))

                }

                mostrarDatos(equipos)

            }




                obtenerEquiposYCrearEncuentros { encuentros ->

                    // Aquí puedes trabajar con la lista de encuentros obtenida
                    encuentrosAdapter = EncuentrosAdapter(encuentros)
                    rvEncuentros.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvEncuentros.adapter = encuentrosAdapter

                }


    }
    fun mostrarDatos(equipos:MutableList<Equipo>) {
        //for para recorrer la tabla
        for (i in 0..11) {
            var hijo: LinearLayout? = tablaFil1.getChildAt(i) as? LinearLayout

            //for para recorrer la fila
            for (j in 0..6) {
                var hijo: TextView? = hijo?.getChildAt(j) as? TextView
                hijo!!.text = equipos[i].getValores(j)
            }
        }
    }


    private fun iniciarVerificacionPeriodica() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                cargarDatos()
                delay(10000) // 10 segundos
            }
        }
    }

    fun obtenerEquiposYCrearEncuentros(callback: (List<Encuentro>) -> Unit) {
        val encuentros: MutableList<Encuentro> = mutableListOf()
        var db = Firebase.firestore

        db.collection("ENCUENTROS")
            .get()
            .addOnSuccessListener { encuentrosSnapshot ->
                val encuentrosData = encuentrosSnapshot.documents
                var equiposProcesados = 0

                for (encuentro in encuentrosData) {
                    val local = encuentro.getString("LOCAL")!!
                    val visitante = encuentro.getString("VISITANTE")!!
                    val jl = encuentro.getLong("JUEGOS_LOCAL")!!.toInt()
                    val jv = encuentro.getLong("JUEGOS_VISITANTE")!!.toInt()
                    val pl = encuentro.getLong("PARTIDOS_LOCAL")!!.toInt()
                    val pv = encuentro.getLong("PARTIDOS_VISITANTE")!!.toInt()

                    db.collection("EQUIPOS").whereEqualTo("NOMBRE", local)
                        .get()
                        .addOnSuccessListener { localSnapshot ->
                            val equipoLocal = localSnapshot.documents[0]

                            val nombreEquipoLocal = equipoLocal.getString("NOMBRE")!!
                            val ejLocal = equipoLocal.getLong("ENCUENTROS_JUGADOS")!!.toInt()
                            val egLocal = equipoLocal.getLong("ENCUENTROS_GANADOS")!!.toInt()
                            val epLocal = equipoLocal.getLong("ENCUENTROS_PERDIDOS")!!.toInt()
                            val pfLocal = equipoLocal.getLong("PARTIDOS_FAVOR")!!.toInt()
                            val pcLocal = equipoLocal.getLong("PARTIDOS_CONTRA")!!.toInt()

                            val equipoLocalObj = Equipo(nombreEquipoLocal, ejLocal, egLocal, epLocal, pfLocal, pcLocal)

                            db.collection("EQUIPOS").whereEqualTo("NOMBRE", visitante)
                                .get()
                                .addOnSuccessListener { visitanteSnapshot ->
                                    val equipoVisitante = visitanteSnapshot.documents[0]

                                    val nombreEquipoVisitante = equipoVisitante.getString("NOMBRE")!!
                                    val ejVisitante = equipoVisitante.getLong("ENCUENTROS_JUGADOS")!!.toInt()
                                    val egVisitante = equipoVisitante.getLong("ENCUENTROS_GANADOS")!!.toInt()
                                    val epVisitante = equipoVisitante.getLong("ENCUENTROS_PERDIDOS")!!.toInt()
                                    val pfVisitante = equipoVisitante.getLong("PARTIDOS_FAVOR")!!.toInt()
                                    val pcVisitante = equipoVisitante.getLong("PARTIDOS_CONTRA")!!.toInt()

                                    val equipoVisitanteObj = Equipo(nombreEquipoVisitante, ejVisitante, egVisitante, epVisitante, pfVisitante, pcVisitante)

                                    encuentros.add(Encuentro(equipoLocalObj, equipoVisitanteObj, jl, jv, pl, pv))
                                    equiposProcesados++

                                    if (equiposProcesados == encuentrosData.size) {
                                        callback(encuentros)
                                    }
                                }
                        }
                }
            }
    }

//






}