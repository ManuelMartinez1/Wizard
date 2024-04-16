package com.example.wizard.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wizard.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LogIn : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.usernameEditText2)
        signInButton = findViewById(R.id.button)

        signInButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LogIn, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = FirebaseFirestore.getInstance()
            val usersRef = db.collection("users")

            usersRef.whereEqualTo("usuario", username)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        // No se encontró ningún usuario con el nombre de usuario proporcionado
                        Toast.makeText(this@LogIn, "Nombre de usuario incorrecto", Toast.LENGTH_SHORT).show()
                    } else {
                        // Se encontró un usuario con el nombre de usuario proporcionado
                        val email = documents.documents[0].getString("correo")
                        if (email != null) {
                            signInWithEmailAndPassword(email, password)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@LogIn, "Error al iniciar sesión: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    val intent = Intent(this, Orchestrator::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Inicio de sesión fallido
                    Toast.makeText(this@LogIn, "Error al iniciar sesión: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}