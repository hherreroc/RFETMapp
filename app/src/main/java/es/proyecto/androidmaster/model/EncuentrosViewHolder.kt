package es.proyecto.androidmaster.model

import android.R.color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.proyecto.androidmaster.R


class EncuentrosViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val tvEquipoLocal:TextView = view.findViewById(R.id.nombreLocal)
    private val tvEquipoVisitante:TextView = view.findViewById(R.id.nombreVisitante)
    private val ivLocal:ImageView = view.findViewById(R.id.imgLocal)
    private val ivVisitante:ImageView = view.findViewById(R.id.imgVisitante)
    private val tvPartidosLocal:TextView = view.findViewById(R.id.partidosLocal)
    private val tvPartidosVisitante:TextView = view.findViewById(R.id.partidosVisitante)



    fun render(encuentro: Encuentro) {
        var local = encuentro.getLocal()
        val visitante = encuentro.getVisitante()


        tvEquipoLocal.text = local.getNombre()
        tvEquipoVisitante.text = visitante.getNombre()
        //Actualizar partidso ganados
        tvPartidosLocal.text = encuentro.getPartidosLocal().toString()
        tvPartidosVisitante.text = encuentro.getPartidosVisitante().toString()

        when(local.getNombre()){
            "ONE VISION PARLA" -> ivLocal.setImageResource(R.drawable.logoparla)
            "LECHE GAZA TENIS DE MESA" -> ivLocal.setImageResource(R.drawable.logogaza)
            "ATLETICO SAN SEBASTIAN" -> ivLocal.setImageResource(R.drawable.logoatss)
            "IRUN LEKA ENEA" -> ivLocal.setImageResource(R.drawable.logoirun)
            "GONDOMAR TM ROSQUILLAS CRISTALEIRO" -> ivLocal.setImageResource(R.drawable.logogondomar)
            "ETM HOTEL MARQUES DE SANTILLANA" -> ivLocal.setImageResource(R.drawable.logotorrelavega)
            "CLUB DEL MAR" -> ivLocal.setImageResource(R.drawable.logoclubdelmar)
            "PUBLIMAX CAI SANTIAGO" -> ivLocal.setImageResource(R.drawable.logopublimax)
            "SEGHOS ESCUELA TENIS DE MESA" -> ivLocal.setImageResource(R.drawable.logoseghos)
            "VILAGARCIA TENIS DE MESA" -> ivLocal.setImageResource(R.drawable.logovilagarcia)
            "UNIVERSIDAD DE BURGOS - TPF" -> ivLocal.setImageResource(R.drawable.logoubu)
            "CTM MILAGROSA" -> ivLocal.setImageResource(R.drawable.logomilagrosa)

        }
        when(visitante.getNombre()){
            "ONE VISION PARLA" -> ivVisitante.setImageResource(R.drawable.logoparla)
            "LECHE GAZA TENIS DE MESA" -> ivVisitante.setImageResource(R.drawable.logogaza)
            "ATLETICO SAN SEBASTIAN" -> ivVisitante.setImageResource(R.drawable.logoatss)
            "IRUN LEKA ENEA" -> ivVisitante.setImageResource(R.drawable.logoirun)
            "GONDOMAR TM ROSQUILLAS CRISTALEIRO" -> ivVisitante.setImageResource(R.drawable.logogondomar)
            "ETM HOTEL MARQUES DE SANTILLANA" -> ivVisitante.setImageResource(R.drawable.logotorrelavega)
            "CLUB DEL MAR" -> ivVisitante.setImageResource(R.drawable.logoclubdelmar)
            "PUBLIMAX CAI SANTIAGO" -> ivVisitante.setImageResource(R.drawable.logopublimax)
            "SEGHOS ESCUELA TENIS DE MESA" -> ivVisitante.setImageResource(R.drawable.logoseghos)
            "VILAGARCIA TENIS DE MESA" -> ivVisitante.setImageResource(R.drawable.logovilagarcia)
            "UNIVERSIDAD DE BURGOS - TPF" -> ivVisitante.setImageResource(R.drawable.logoubu)
            "CTM MILAGROSA" -> ivVisitante.setImageResource(R.drawable.logomilagrosa)

        }

    }
}