package es.proyecto.androidmaster.model

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.proyecto.androidmaster.FirstActivity
import es.proyecto.androidmaster.R
import es.proyecto.androidmaster.RankingFragment

class JugadorDetalles : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var btnVolver:Button
    private lateinit var tvNombre:TextView
    private lateinit var tvLicencia:TextView
    private lateinit var tvClub:TextView
    private lateinit var tvEquipo:TextView
    private lateinit var tvNacionalidad:TextView
    private lateinit var tvPuntos:TextView
    private lateinit var tvCategoria:TextView






    companion object{
        const val LICENCIA = "LICENCIA"
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jugador_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val licencia = intent.getIntExtra(LICENCIA,0)
        tvClub = findViewById(R.id.clubJugador)
        tvEquipo = findViewById(R.id.equipoJugador)
        tvLicencia = findViewById(R.id.licenciaJugador)
        tvCategoria = findViewById(R.id.categoriaJugador)
        tvNombre = findViewById(R.id.nombreJugador)
        tvPuntos = findViewById(R.id.puntosJugador)
        tvNacionalidad = findViewById(R.id.nacionalidadJugador)


        btnVolver = findViewById(R.id.btnVolver)
        db = Firebase.firestore
        db.collection("JUGADORES").whereEqualTo("LICENCIA",licencia).get().addOnSuccessListener {

            val jugador = it.documents[0]
                var nombre = jugador.getString("NOMBRE")!!
                var puntos = jugador.getLong("PUNTOS")?.toInt()!!
                var nacionalidad = jugador.getString("NACIONALIDAD")!!
                var licencia = jugador.getLong("LICENCIA")?.toInt()!!
                var categoria = Jugador.Categorias.valueOf(jugador.getString("CATEGORIA")!!)
                var tipoLicencia = jugador.getString("TIPO_LICENCIA")!!

                val equipoRef = jugador.get("EQUIPO") as DocumentReference
                val clubRef = jugador.get("CLUB") as DocumentReference

                // Obtiene los datos del club
                clubRef.get().addOnSuccessListener { clubSnapshot ->
                    val club = clubSnapshot.getString("NOMBRE")
                    tvClub.text = club
                }
                equipoRef.get().addOnSuccessListener { clubSnapshot ->
                    val equipo = clubSnapshot.getString("NOMBRE")
                    tvEquipo.text = equipo
                }

                tvNombre.text = nombre
                tvLicencia.text = "TIPO: '" + tipoLicencia + "' " + licencia
                tvCategoria.text = categoria.toString()
                tvNacionalidad.text = nacionalidad
                tvPuntos.text = puntos.toString()










        }

        btnVolver.setOnClickListener{ volver()}
    }

    private fun volver() {

        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)

    }


}