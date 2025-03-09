package es.proyecto.androidmaster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.proyecto.androidmaster.model.Encuentro
import es.proyecto.androidmaster.model.Jugador

class ActaActivity : AppCompatActivity() {



    private lateinit var tvEquipoLocal: TextView
    private lateinit var tvEquipoVisitante: TextView
    private lateinit var ivLocal: ImageView
    private lateinit var ivVisitante: ImageView
    private lateinit var spA: Spinner
    private lateinit var spB: Spinner
    private lateinit var spC: Spinner
    private lateinit var spX: Spinner
    private lateinit var spY: Spinner
    private lateinit var spZ: Spinner
    private lateinit var db: FirebaseFirestore
    private lateinit var local: String
    private lateinit var visitante: String
    private lateinit var jugadoresLocal: MutableList<Jugador>
    private lateinit var jugadoresVisitante: MutableList<Jugador>
    private lateinit var arbitro: String
    private lateinit var btnAlineacion: Button
    private lateinit var btnVolver: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarComponentes()
        cargarEncuentros()
        btnVolver.setOnClickListener(){
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)

        }
        btnAlineacion.setOnClickListener() {
            if (spA.selectedItem.toString() == spB.selectedItem.toString() || spA.selectedItem.toString() == spC.selectedItem.toString() || spB.selectedItem.toString() == spC.selectedItem.toString()
                || spX.selectedItem.toString() == spY.selectedItem.toString() || spX.selectedItem.toString() == spZ.selectedItem.toString() || spY.selectedItem.toString() == spZ.selectedItem.toString()
                ) {
                Toast.makeText(
                    this,
                    "UN JUGADOR NO PUEDE ESTAR EN DOS POSICIONES DIFERENTES",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                val intent = Intent(this, ResultadoActivity::class.java)
                intent.putExtra("JUGADOR_A", spA.selectedItem.toString())
                intent.putExtra("JUGADOR_B", spB.selectedItem.toString())
                intent.putExtra("JUGADOR_C", spC.selectedItem.toString())
                intent.putExtra("JUGADOR_X", spX.selectedItem.toString())
                intent.putExtra("JUGADOR_Y", spY.selectedItem.toString())
                intent.putExtra("JUGADOR_Z", spZ.selectedItem.toString())
                intent.putExtra("ARBITRO", arbitro)
                intent.putExtra("LOCAL", local)
                intent.putExtra("VISITANTE", visitante)
                startActivity(intent)
            }
        }


    }


    private fun inicializarComponentes() {
        tvEquipoLocal = findViewById(R.id.nombreLocal)
        tvEquipoVisitante = findViewById(R.id.nombreVisitante)
        ivLocal = findViewById(R.id.imgLocal)
        ivVisitante = findViewById(R.id.imgVisitante)
        spA = findViewById(R.id.jugadorA)
        spB = findViewById(R.id.jugadorB)
        spC = findViewById(R.id.jugadorC)
        spX = findViewById(R.id.jugadorX)
        spY = findViewById(R.id.jugadorY)
        spZ = findViewById(R.id.jugadorZ)
        db = Firebase.firestore
        arbitro = intent.getStringExtra("ARBITRO")!!
        jugadoresLocal = mutableListOf()
        jugadoresVisitante = mutableListOf()
        btnAlineacion = findViewById(R.id.btnAlineacion)
        btnVolver = findViewById(R.id.btnVolver)


    }

    private fun cargarEncuentros() {
        val referenciaArbitro = db.collection("ARBITROS").document(arbitro)

        db.collection("ENCUENTROS").whereEqualTo("ARBITRO", referenciaArbitro).get()
            .addOnSuccessListener {
                val datos = it.documents
                for (encuentro in datos) {
                    local = encuentro.getString("LOCAL").toString()
                    visitante = encuentro.getString("VISITANTE").toString()
                    mostrarEncuentro(local, visitante)
                }

                cargarJugadores()


            }


    }

    fun cargarJugadores() {
        val referenciaLocal = db.collection("EQUIPOS").document(local)
        val referenciaVisitante = db.collection("EQUIPOS").document(visitante)

        db.collection("JUGADORES").whereEqualTo("EQUIPO", referenciaLocal)

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


                    jugadoresLocal.add(
                        Jugador(
                            licencia,
                            nombre,
                            tipoLicencia,
                            (categoria),
                            puntos,
                            nacionalidad
                        )
                    )

                }

            }
        db.collection("JUGADORES").whereEqualTo("EQUIPO", referenciaVisitante)

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


                    jugadoresVisitante.add(
                        Jugador(
                            licencia,
                            nombre,
                            tipoLicencia,
                            (categoria),
                            puntos,
                            nacionalidad
                        )
                    )

                }
                mostrarJugadores()

            }

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

    fun mostrarJugadores() {
        val itemLocal = sacarNombres(jugadoresLocal)
        val itemVisitante = sacarNombres(jugadoresVisitante)


        val adapterLocal = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemLocal)
        adapterLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapterVisitante =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, itemVisitante)
        adapterLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spA.adapter = adapterLocal
        spB.adapter = adapterLocal
        spC.adapter = adapterLocal

        spX.adapter = adapterVisitante
        spY.adapter = adapterVisitante
        spZ.adapter = adapterVisitante

    }
    private fun sacarNombres(jugadores: MutableList<Jugador>): List<String> {
        var items = mutableListOf<String>()
        for (jugador in jugadores) {
            items.add(jugador.getNombre())
        }
        return items

    }
}