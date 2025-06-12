package com.gabrielly.projintegrador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextMatricula: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonCriarConta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMatricula = findViewById(R.id.editTextMatricula)
        editTextSenha = findViewById(R.id.editTextSenha)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonCriarConta = findViewById(R.id.buttonAjuda)

        val prefs = getSharedPreferences("UsuariosPrefs", Context.MODE_PRIVATE)
        val prefsLogin = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            val matricula = editTextMatricula.text.toString().trim()
            val senha = editTextSenha.text.toString().trim()

            // Login do professor
            if (matricula == "professor@ifes.edu.br" && senha == "napne") {
                prefsLogin.edit()
                    .putString("usuarioLogado", matricula)
                    .putString("tipoUsuario", "professor")
                    .apply()

                Toast.makeText(this, "Login do professor realizado com sucesso!", Toast.LENGTH_SHORT).show()

                // Vai para a área do professor
                startActivity(Intent(this, MainActivity3::class.java))
                finish()
                return@setOnClickListener
            }

            // Login do aluno (verifica nos prefs)
            val senhaSalva = prefs.getString(matricula, null)
            if (senhaSalva != null && senhaSalva == senha) {
                prefsLogin.edit()
                    .putString("usuarioLogado", matricula)
                    .putString("tipoUsuario", "aluno")
                    .apply()

                Toast.makeText(this, "Login do aluno realizado com sucesso!", Toast.LENGTH_SHORT).show()

                // Vai para a área do aluno
                startActivity(Intent(this, MainActivity2::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login ou senha incorretos!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
