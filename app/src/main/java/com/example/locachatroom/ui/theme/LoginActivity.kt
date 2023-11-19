package com.example.locachatroom.ui.theme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.locachatroom.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Bind UI components
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar)

        // Set click listeners
        findViewById<View>(R.id.email_sign_in_button).setOnClickListener { signIn() }
        findViewById<View>(R.id.link_register).setOnClickListener { navigateToRegister() }
    }

    private fun signIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Navigate to Main Activity
                     val intent = Intent(this, MainActivity::class.java)
                     startActivity(intent)
                     finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToRegister() {
        // Navigate to Register Activity
         val intent = Intent(this, RegisterActivity::class.java)
         startActivity(intent)
    }
}
