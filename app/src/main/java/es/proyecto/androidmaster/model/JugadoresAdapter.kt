package es.proyecto.androidmaster.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.proyecto.androidmaster.R

class JugadoresAdapter(private var jugadores:List<Jugador>,
                       private val onItemSelected:(Int) -> Unit
) :
    RecyclerView.Adapter<JugadoresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false)
        return JugadoresViewHolder(view)
    }

    override fun getItemCount(): Int = jugadores.size

    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int) {
        holder.render(jugadores[position], onItemSelected)
    }

    fun setJugadores(jugadores: MutableList<Jugador>){
        this.jugadores = jugadores
    }
}