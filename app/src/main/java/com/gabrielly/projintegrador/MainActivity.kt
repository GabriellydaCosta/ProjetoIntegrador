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

        // LOGIN
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

                            // Busca o campo "tipo" dentro do nó do usuário no Firebase
                            database.child("users").child(uid).get()
                                .addOnSuccessListener { snapshot ->
                                    val tipoUsuario = snapshot.child("tipo").getValue(String::class.java) ?: "aluno"
                                    editor.putString("tipoUsuario", tipoUsuario)
                                    editor.apply()

                                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                                    // Redireciona para tela correta conforme tipo do usuário
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

        // CRIAR CONTA
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

                            // Ajuste aqui: definir lista de emails que são professores
                            val emailsProfessores = listOf("professor@ifes.edu.br")

                            val tipoUsuario = if (email in emailsProfessores) "professor" else "aluno"

                            // Salva um objeto com campo "tipo"
                            val dadosUsuario = mapOf("tipo" to tipoUsuario)

                            database.child("users").child(uid).setValue(dadosUsuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()

                                    // Redireciona para tela adequada logo após criação
                                    if (tipoUsuario == "professor") {
                                        startActivity(Intent(this, MainActivity3::class.java))
                                    } else {
                                        startActivity(Intent(this, MainActivity2::class.java))
                                    }
                                    finish()
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
