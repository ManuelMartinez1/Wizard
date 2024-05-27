package com.example.wizard.ui.user

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wizard.R
import com.example.wizard.ui.orchestrator.Orchestrator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {
    private lateinit var registerButton: Button
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var fechaNacimientoEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var confirmarContrasenaEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Inicializar vistas
        registerButton = findViewById(R.id.register)
        nombreEditText = findViewById(R.id.nombre)
        apellidoEditText = findViewById(R.id.apellido)
        fechaNacimientoEditText = findViewById(R.id.editTextDate)
        usuarioEditText = findViewById(R.id.usuario)
        correoEditText = findViewById(R.id.correo_electronico)
        contrasenaEditText = findViewById(R.id.contrasena)
        confirmarContrasenaEditText = findViewById(R.id.confirmar_contrasena)

        fechaNacimientoEditText.setOnClickListener {
            mostrarSelectorFecha()
        }

        registerButton.setOnClickListener {
            // Obtener los valores de los campos de texto
            val nombre = nombreEditText.text.toString().trim()
            val apellido = apellidoEditText.text.toString().trim()
            val fechaNacimiento = fechaNacimientoEditText.text.toString().trim()
            val usuario = usuarioEditText.text.toString().trim()
            val correo = correoEditText.text.toString().trim()
            val contrasena = contrasenaEditText.text.toString().trim()
            val confirmarContrasena = confirmarContrasenaEditText.text.toString().trim()

            // Validar campos obligatorios
            if (nombre.isEmpty() || apellido.isEmpty() || fechaNacimiento.isEmpty() ||
                usuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                Toast.makeText(this@SignUp, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar formato de correo electrónico
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this@SignUp, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar contraseña segura (longitud mínima)
            if (contrasena.length < 6) {
                Toast.makeText(this@SignUp, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar coincidencia de contraseña
            if (contrasena != confirmarContrasena) {
                Toast.makeText(this@SignUp, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Llamar al método de registro con Firebase Authentication y Firestore
            registrarUsuario(nombre, apellido, fechaNacimiento, usuario, correo, contrasena)
        }



        // Aplicar padding para la barra de estado y barra de navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun mostrarSelectorFecha() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val fechaSeleccionada = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                fechaNacimientoEditText.setText(fechaSeleccionada)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    // Método para registrar un usuario utilizando Firebase Authentication y guardar los datos en Firestore
    private fun registrarUsuario(nombre: String, apellido: String, fechaNacimiento: String, usuario: String, correo: String, contrasena: String) {
        val mAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        mAuth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    val user = mAuth.currentUser
                    // Guardar los datos del usuario en Firestore
                    val userData = hashMapOf(
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "fecha_nacimiento" to fechaNacimiento,
                        "usuario" to usuario,
                        "correo" to correo
                    )
                    user?.uid?.let { userId ->
                        db.collection("users").document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                // Datos del usuario guardados exitosamente en Firestore
                                val intent = Intent(this, Orchestrator::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                // Error al guardar los datos del usuario en Firestore
                                Toast.makeText(this@SignUp, "Error al guardar los datos del usuario: ${e.message}",
                                    Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Registro fallido
                    Toast.makeText(this@SignUp, "Error en el registro: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
