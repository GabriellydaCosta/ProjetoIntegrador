package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var editTextMatricula: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonCriarConta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        editTextMatricula = findViewById(R.id.editTextMatricula)
        editTextSenha = findViewById(R.id.editTextSenha)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonCriarConta = findViewById(R.id.buttonAjuda)

        buttonLogin.setOnClickListener {
            val email = editTextMatricula.text.toString().trim()
            val senha = editTextSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val usuario = auth.currentUser
                        if (usuario != null) {
                            val uid = usuario.uid
                            val database = FirebaseDatabase.getInstance().reference
                            val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
                            val editor = prefsLogin.edit()

                            // Busca o tipo do usuário no Firebase Database **direto no nó uid**
                            database.child("users").child(uid).get()
                                .addOnSuccessListener { snapshot ->
                                    // Pega o valor direto (ex: "professor" ou "aluno")
                                    val tipoUsuario = snapshot.getValue(String::class.java) ?: "aluno"
                                    editor.putString("tipoUsuario", tipoUsuario)
                                    editor.apply()

                                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                                    // Redireciona para tela adequada
                                    if (tipoUsuario == "professor") {
                                        startActivity(Intent(this, MainActivity3::class.java))
                                    } else {
                                        startActivity(Intent(this, MainActivity2::class.java))
                                    }
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Erro ao buscar tipo do usuário.", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "Usuário não encontrado após login.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Erro no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        buttonCriarConta.setOnClickListener {
            val email = editTextMatricula.text.toString().trim()
            val senha = editTextSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val usuario = auth.currentUser
                        if (usuario != null) {
                            val uid = usuario.uid
                            val database = FirebaseDatabase.getInstance().reference

                            // Define tipoUsuario com base no email
                            val tipoUsuario = if (email == "professor@ifes.edu.br") "professor" else "aluno"

                            // Salva tipoUsuario no Firebase Database como valor direto no nó uid
                            database.child("users").child(uid).setValue(tipoUsuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Erro ao salvar tipo de usuário: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(this, "Erro ao criar conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
