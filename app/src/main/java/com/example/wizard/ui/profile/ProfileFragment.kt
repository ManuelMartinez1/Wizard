package com.example.wizard.ui.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.example.wizard.R
import com.example.wizard.ui.MainActivity
import com.example.wizard.ui.bets_profile.BetsProfileFragment
import com.example.wizard.ui.stats_profile.StatsProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var betsFragment: BetsProfileFragment
    private lateinit var statsFragment: StatsProfileFragment
    private lateinit var presenter: ProfileContract.Presenter
    private lateinit var usernameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //toggle de bets y stats
        betsFragment = BetsProfileFragment()
        statsFragment = StatsProfileFragment()
        presenter = ProfilePresenter(this)

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, betsFragment)
            .commit()

        val toggle = view.findViewById<RadioGroup>(R.id.toggle)
        toggle.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.betsposts -> presenter.onBetsSelected()
                R.id.stats -> presenter.onStatsSelected()
            }
        }

        // para boton de logout
        view.findViewById<ImageView>(R.id.logout_button).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        //nombre del user
        usernameTextView = view.findViewById(R.id.nombre_completo)
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        if (currentUserEmail != null) {
            //buscar documento en firestore
            val db = FirebaseFirestore.getInstance()
            val usersRef = db.collection("users")
            usersRef.whereEqualTo("correo", currentUserEmail)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        //agarrar nombre y apellido del user con la sesion activa
                        val nombre = documents.documents[0].getString("nombre")
                        val apellido = documents.documents[0].getString("apellido")
                        //poner nombre y apellido en textview
                        usernameTextView.text = "$nombre $apellido"
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error al obtener los datos del usuario: $e")
                }
        }
    }

    override fun showBetsFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, betsFragment)
            .commit()
    }

    override fun showStatsFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, statsFragment)
            .commit()
    }
}
