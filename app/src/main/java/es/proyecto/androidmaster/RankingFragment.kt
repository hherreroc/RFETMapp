package es.proyecto.androidmaster

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.proyecto.androidmaster.model.Jugador
import es.proyecto.androidmaster.model.JugadorDetalles
import es.proyecto.androidmaster.model.JugadoresAdapter


class RankingFragment : Fragment() {

    private lateinit var jugadoresAdapter: JugadoresAdapter
    private lateinit var jugadores: MutableList<Jugador>
    private lateinit var db: FirebaseFirestore
    private lateinit var rvJugadores : RecyclerView
    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inizializar(view)
        initUI()
    }


    private fun inizializar(view: View) {
        jugadores = mutableListOf()
        db = Firebase.firestore
        rvJugadores = view.findViewById(R.id.rvJugadores)
        searchView = view.findViewById(R.id.searchView)
    }

    private fun initUI() {

        db.collection("JUGADORES")
            .orderBy("PUNTOS", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {

                val datos = it.documents
                for (jugador in datos) {
                    var nombre = jugador.getString("NOMBRE")!!
                    var puntos = jugador.getLong("PUNTOS")?.toInt()!!
                    var nacionalidad = jugador.getString("NACIONALIDAD")!!
                    var licencia = jugador.getLong("LICENCIA")?.toInt()!!
                    var categoria = Jugador.Categorias.valueOf(jugador.getString("CATEGORIA")!!)
                    var tipoLicencia = jugador.getString("TIPO_LICENCIA")!!


                    jugadores.add(Jugador(licencia, nombre, tipoLicencia, (categoria), puntos, nacionalidad))

                }


                jugadoresAdapter = JugadoresAdapter(jugadores){ mostrarDetalles(it)}
                rvJugadores.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvJugadores.adapter = jugadoresAdapter

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean = false
                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Filtrar la lista de jugadores según el texto ingresado
                        jugadoresAdapter.setJugadores(filtrarJugadores(newText).toMutableList())
                        rvJugadores.adapter = jugadoresAdapter

                        return true
                    }
                })
            }

    }

    private fun filtrarJugadores(nombre: String?): List<Jugador>{
        if (nombre!=null){
            val texto = nombre.uppercase()
            return jugadores.filter { jugador ->
                jugador.getNombre().uppercase().startsWith(texto)
            }
        }else{
            return emptyList()
        }

    }

    private fun mostrarDetalles(licencia: Int){
        val intent = Intent(requireContext(), JugadorDetalles::class.java)
        intent.putExtra(JugadorDetalles.LICENCIA, licencia)
        startActivity(intent)
    }



}