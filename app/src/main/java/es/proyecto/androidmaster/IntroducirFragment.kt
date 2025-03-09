package es.proyecto.androidmaster

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.proyecto.androidmaster.model.Jugador
import es.proyecto.androidmaster.model.JugadorDetalles
import org.checkerframework.checker.units.qual.A

class IntroducirFragment : Fragment() {


    private lateinit var etLicencia:EditText
    private lateinit var etPassword:EditText
    private lateinit var btnIniciar:Button
    private lateinit var imgGoogle:ImageView
    private lateinit var db: FirebaseFirestore




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introducir, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inizializar(view)
        btnIniciar.setOnClickListener{ comprobarCredenciales() }
    }




    private fun inizializar(view: View) {

        //imgGoogle = view.findViewById(R.id.imgGoogle)
        etLicencia = view.findViewById(R.id.etLicencia)
        etPassword = view.findViewById(R.id.etPassword)
        btnIniciar = view.findViewById(R.id.btnInicarSesion)

        db = Firebase.firestore

    }

    private fun comprobarCredenciales() {

        db.collection("ARBITROS").whereEqualTo("LICENCIA", etLicencia.text.toString().toInt())
            .whereEqualTo("CONTRASEÑA", etPassword.text.toString())
            .get()
            .addOnSuccessListener { querySnapshot ->
                val datos = querySnapshot.documents

                if (datos.isEmpty()) {
                    etPassword.setText("")
                    etLicencia.setText("")
                    etPassword.hint = "Contraseña incorrecta"
                    etLicencia.hint = "Licencia incorrecta"
                } else {
                    var nombre = ""
                    for (arbitro in datos){
                        nombre = arbitro.getString("NOMBRE").toString()
                    }

                    val intent = Intent(requireContext(), ActaActivity::class.java)
                    intent.putExtra("ARBITRO", nombre)
                    startActivity(intent)

                }
            }
    }




}