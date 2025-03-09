package es.proyecto.androidmaster.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.proyecto.androidmaster.R

class EncuentrosAdapter(private var encuentros:List<Encuentro>) : RecyclerView.Adapter<EncuentrosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuentrosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_encuentro, parent, false)
        return EncuentrosViewHolder(view)
    }



    override fun onBindViewHolder(holder: EncuentrosViewHolder, position: Int) {
        holder.render(encuentros[position])
    }

    override fun getItemCount(): Int = encuentros.size
}