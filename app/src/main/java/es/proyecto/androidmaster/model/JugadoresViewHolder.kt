package es.proyecto.androidmaster.model

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.proyecto.androidmaster.R

class JugadoresViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var tvNombre = view.findViewById<TextView>(R.id.nombreJugador)
    private var tvPuntos = view.findViewById<TextView>(R.id.puntosJugador)


    fun render(jugador: Jugador, onItemSelected:(Int) -> Unit){
        tvNombre.text = jugador.getNombre()
        tvPuntos.text = jugador.getPuntos().toString()


        tvNombre.setOnClickListener{ onItemSelected(jugador.getLicencia()) }

    }

}