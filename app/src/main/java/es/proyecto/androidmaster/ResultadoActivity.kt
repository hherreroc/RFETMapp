package es.proyecto.androidmaster

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResultadoActivity : AppCompatActivity() {

    private var listaLetrasLocal = listOf("A", "B", "C")
    private var listaLetrasVisitante = listOf("X", "Y", "Z")


    private lateinit var tvEquipoLocal: TextView
    private lateinit var tvEquipoVisitante: TextView
    private lateinit var ivLocal: ImageView
    private lateinit var ivVisitante: ImageView
    private lateinit var local: String
    private lateinit var visitante: String
    private lateinit var arbitro: String
    private lateinit var btnVolver: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var tvLetraA: TextView
    private lateinit var tvLetraX: TextView
    private lateinit var tvSetA: TextView
    private lateinit var tvSetX: TextView
    private lateinit var tvJugadorA: TextView
    private lateinit var tvJugadorX: TextView
    private lateinit var tvPartidosLocal :TextView
    private lateinit var tvPartidosVisitante :TextView


    private lateinit var btnPlusA: FloatingActionButton
    private lateinit var btnPlusX: FloatingActionButton
    private lateinit var btnMinusA: FloatingActionButton
    private lateinit var btnMinusX: FloatingActionButton

    private lateinit var tvPuntosA: TextView
    private lateinit var tvPuntosX: TextView

    private var puntosA = 0
    private var puntosX = 0

    private var setA = 0
    private var setX = 0

    private var partidosLocal = 0
    private var partidosVisitante = 0







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        inicializarComponentes()
        mostrarEncuentro(local, visitante)
        btnPlusX.setOnClickListener(){ sumarX() }
        btnPlusA.setOnClickListener(){ sumarA() }
        btnMinusX.setOnClickListener(){ restarX() }
        btnMinusA.setOnClickListener(){ restarA() }
        btnVolver.setOnClickListener(){ navigateBack() }
    }

    private fun navigateBack() {
        if (btnVolver.isEnabled){
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }
    }

    private fun restarA() {
        if (puntosA > 0){
            puntosA--
            tvPuntosA.text = puntosA.toString()
        }

    }

    private fun restarX() {
        if (puntosX > 0){
            puntosX--
            tvPuntosX.text = puntosX.toString()
        }

    }

    private fun sumarX() {
        puntosX ++
        tvPuntosX.text = puntosX.toString()

        if (puntosX >= 11 && puntosX - puntosA >= 2) {
            puntosA = 0
            puntosX = 0
            setX ++
            tvPuntosX.text = puntosX.toString()
            tvPuntosA.text = puntosA.toString()
            tvSetX.text = setX.toString()
            comprobarSets()
        }

    }



    private fun sumarA() {

        puntosA ++
        tvPuntosA.text = puntosA.toString()

        if (puntosA >= 11 && puntosA - puntosX >= 2) {
            puntosA = 0
            puntosX = 0
            setA++
            tvPuntosX.text = puntosX.toString()
            tvPuntosA.text = puntosA.toString()
            tvSetA.text = setA.toString()
            comprobarSets()



        }


    }




    private fun inicializarComponentes() {
        tvEquipoLocal = findViewById(R.id.nombreLocal)
        tvEquipoVisitante = findViewById(R.id.nombreVisitante)
        ivLocal = findViewById(R.id.imgLocal)
        ivVisitante = findViewById(R.id.imgVisitante)
        btnPlusX = findViewById(R.id.btnPlusX)
        btnPlusA = findViewById(R.id.btnPlusA)
        btnMinusA = findViewById(R.id.btnMinusA)
        btnMinusX = findViewById(R.id.btnMinusX)
        tvPuntosA = findViewById(R.id.tvPuntosA)
        tvPuntosX = findViewById(R.id.tvPuntosX)
        tvSetA = findViewById(R.id.tvSetA)
        tvSetX = findViewById(R.id.tvSetX)
        tvLetraA = findViewById(R.id.tvLetraA)
        tvLetraX = findViewById(R.id.tvLetraX)

        tvPartidosLocal = findViewById(R.id.partidosLocal)
        tvPartidosVisitante = findViewById(R.id.partidosVisitante)


        db = Firebase.firestore
        arbitro = intent.getStringExtra("ARBITRO")!!
        btnVolver = findViewById(R.id.btnVolver)
        local = intent.getStringExtra("LOCAL")!!
        visitante = intent.getStringExtra("VISITANTE")!!
        tvJugadorA = findViewById(R.id.tvJugadorA)
        tvJugadorA.text = intent.getStringExtra("JUGADOR_A")!!
        tvJugadorX = findViewById(R.id.tvJugadorX)
        tvJugadorX.text = intent.getStringExtra("JUGADOR_X")!!
    }


    fun mostrarEncuentro(local: String, visitante: String) {


        tvEquipoLocal.text = local
        tvEquipoVisitante.text = visitante


        when (local) {
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
        when (visitante) {
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

    private fun comprobarSets() {
        if (setA == 3){
            sumarSets(local)
            setA = 0
            setX = 0
            puntosX = 0
            puntosA = 0
            partidosLocal ++

            tvSetA.text = "0"
            tvSetX.text = "0"
            tvPartidosLocal.text = partidosLocal.toString()


            if(partidosLocal + partidosVisitante < listaLetrasLocal.size){
                tvJugadorA.text = intent.getStringExtra("JUGADOR_" + listaLetrasLocal[partidosLocal + partidosVisitante])
                tvJugadorX.text = intent.getStringExtra("JUGADOR_" + listaLetrasVisitante[partidosLocal + partidosVisitante])
                tvLetraA.text = listaLetrasLocal[partidosLocal + partidosVisitante]
                tvLetraX.text = listaLetrasVisitante[partidosLocal + partidosVisitante]
            }else if(partidosLocal + partidosVisitante == 3){
                Toast.makeText(this, "ENCUENTRO FINALIZADO POR FAVOR VUELVE AL INICIO", Toast.LENGTH_LONG).show()
                btnVolver.isEnabled = true
                btnPlusA.isEnabled = false
                btnPlusX.isEnabled = false
                btnMinusA.isEnabled = false
                btnMinusX.isEnabled = false
                sumarEncuentro(local)
                restarEncuentro(visitante)

            }

            subirResultado(local)
            restarResultado(visitante)



        }else if (setX == 3){
            sumarSets(local)
            setA = 0
            setX = 0
            puntosX = 0
            puntosA = 0
            partidosVisitante ++

            tvSetA.text = "0"
            tvSetX.text = "0"
            tvPartidosVisitante.text = partidosVisitante.toString()

            if(partidosLocal + partidosVisitante < listaLetrasLocal.size){
                tvJugadorA.text = intent.getStringExtra("JUGADOR_" + listaLetrasLocal[partidosLocal + partidosVisitante])
                tvJugadorX.text = intent.getStringExtra("JUGADOR_" + listaLetrasVisitante[partidosLocal + partidosVisitante])
                tvLetraA.text = listaLetrasLocal[partidosLocal + partidosVisitante]
                tvLetraX.text = listaLetrasVisitante[partidosLocal + partidosVisitante]
            }else if(partidosLocal + partidosVisitante == 3){
                Toast.makeText(this, "ENCUENTRO FINALIZADO POR FAVOR VUELVE AL INICIO", Toast.LENGTH_LONG).show()
                btnVolver.isEnabled = true
                btnPlusA.isEnabled = false
                btnPlusX.isEnabled = false
                btnMinusA.isEnabled = false
                btnMinusX.isEnabled = false

                sumarEncuentro(visitante)
                restarEncuentro(local)
            }


            subirResultado(visitante)
            restarResultado(local)



        }

    }

    private fun sumarSets(local: String) {
        var setsA = setA
        var setsX = setX
         db.collection("ENCUENTROS").whereEqualTo("LOCAL", local).get().addOnSuccessListener {
             val encuentroRef = it.documents[0].reference
             encuentroRef.get()
                 .addOnSuccessListener { document ->
                     if (document.exists()) {
                         var juegosLocal = document.getLong("JUEGOS_LOCAL") ?: 0
                         var juegosVisitante = document.getLong("JUEGOS_VISITANTE") ?: 0
                         var pLocal = document.getLong("PARTIDOS_LOCAL") ?: 0
                         var pVisitante = document.getLong("PARTIDOS_VISITANTE") ?: 0


                         juegosLocal += setsA
                         juegosVisitante += setsX
                         pLocal = partidosLocal.toLong()
                         pVisitante = partidosVisitante.toLong()

                         val datosAActualizar = hashMapOf("JUEGOS_LOCAL" to juegosLocal, "JUEGOS_VISITANTE" to juegosVisitante, "PARTIDOS_LOCAL" to pLocal, "PARTIDOS_VISITANTE" to pVisitante,
                         )
                         encuentroRef.update(datosAActualizar as Map<String, Any>)
                             .addOnSuccessListener {
                                 Toast.makeText(this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show()
                             }

                     }
                 }





        }


    }


    private fun restarEncuentro(equipo: String) {
        db.collection("EQUIPOS").document(equipo)
        val equipoRef = db.collection("EQUIPOS").document(equipo)
        equipoRef.get()
            .addOnSuccessListener{ document ->
                if (document.exists()) {
                    // Obtener el valor actual del campo "PARTIDOS_GANADOS"
                    var encuentrosPerdidos = document.getLong("ENCUENTROS_PERDIDOS")!!
                    var encuentrosJugados = document.getLong("ENCUENTROS_JUGADOS")!!

                    encuentrosPerdidos ++
                    encuentrosJugados ++


                    val datosAActualizar = hashMapOf(
                        "ENCUENTROS_PERDIDOS" to encuentrosPerdidos,
                        "ENCUENTROS_JUGADOS" to encuentrosJugados,


                    )

                    equipoRef.update(datosAActualizar as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "ENCUENTRO SUBIDO CON EXITO", Toast.LENGTH_SHORT)
                        }
                        .addOnFailureListener { e ->
                            // Error: No se pudo actualizar el campo "PARTIDOS_GANADOS"
                            Log.w(TAG, "Error al actualizar PARTIDOS_GANADOS: ", e)
                        }
                } else {
                    Log.d(TAG, "El documento $equipo no existe.")
                }
            }


    }

    private fun sumarEncuentro(equipo:String) {
        db.collection("EQUIPOS").document(equipo)
        val equipoRef = db.collection("EQUIPOS").document(equipo)
        equipoRef.get()
            .addOnSuccessListener{ document ->
                if (document.exists()) {
                    // Obtener el valor actual del campo "PARTIDOS_GANADOS"
                    var encuentrosFavor = document.getLong("ENCUENTROS_GANADOS")!!
                    var encuentrosJugados = document.getLong("ENCUENTROS_JUGADOS")!!
                    var puntos = document.getLong("PUNTOS")!!

                    encuentrosFavor ++
                    encuentrosJugados ++
                    puntos += 2


                    val datosAActualizar = hashMapOf(
                        "ENCUENTROS_GANADOS" to encuentrosFavor,
                        "ENCUENTROS_JUGADOS" to encuentrosJugados,
                        "PUNTOS" to puntos


                    )

                    equipoRef.update(datosAActualizar as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "ENCUENTRO SUBIDO CON EXITO", Toast.LENGTH_SHORT)
                        }
                        .addOnFailureListener { e ->
                            // Error: No se pudo actualizar el campo "PARTIDOS_GANADOS"
                            Log.w(TAG, "Error al actualizar PARTIDOS_GANADOS: ", e)
                        }
                } else {
                    Log.d(TAG, "El documento $equipo no existe.")
                }
            }

    }

    private fun subirResultado(equipo:String) {
        db.collection("EQUIPOS").document(equipo)
        val equipoRef = db.collection("EQUIPOS").document(equipo)

        equipoRef.get()
            .addOnSuccessListener{ document ->
                if (document.exists()) {
                    // Obtener el valor actual del campo "PARTIDOS_GANADOS"
                    var partidosFavor = document.getLong("PARTIDOS_FAVOR")!!


                    partidosFavor ++

                    // Actualizar el campo "PARTIDOS_GANADOS" con el nuevo valor
                    val datosAActualizar = hashMapOf(
                        "PARTIDOS_FAVOR" to partidosFavor
                    )

                    equipoRef.update(datosAActualizar as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "PARTIDO SUBIDO CON EXITO", Toast.LENGTH_SHORT)
                        }
                        .addOnFailureListener { e ->
                            // Error: No se pudo actualizar el campo "PARTIDOS_GANADOS"
                            Log.w(TAG, "Error al actualizar PARTIDOS_GANADOS: ", e)
                        }
                } else {
                    Log.d(TAG, "El documento $equipo no existe.")
                }
            }





    }
    private fun restarResultado(equipo:String) {
        db.collection("EQUIPOS").document(equipo)
        val equipoRef = db.collection("EQUIPOS").document(equipo)

        equipoRef.get()
            .addOnSuccessListener{ document ->
                if (document.exists()) {
                    // Obtener el valor actual del campo "PARTIDOS_GANADOS"
                    var partidosContra = document.getLong("PARTIDOS_CONTRA")!!


                    partidosContra ++

                    // Actualizar el campo "PARTIDOS_GANADOS" con el nuevo valor
                    val datosAActualizar = hashMapOf(
                        "PARTIDOS_CONTRA" to partidosContra
                    )

                    equipoRef.update(datosAActualizar as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "PARTIDO SUBIDO CON EXITO", Toast.LENGTH_SHORT)
                        }
                        .addOnFailureListener { e ->
                            // Error: No se pudo actualizar el campo "PARTIDOS_GANADOS"
                            Log.w(TAG, "Error al actualizar PARTIDOS_GANADOS: ", e)
                        }
                } else {
                    Log.d(TAG, "El documento $equipo no existe.")
                }
            }



    }

}