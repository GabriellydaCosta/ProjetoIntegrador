package com.gabrielly.projintegrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.content.Context
import android.widget.*


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

        buttonLogin.setOnClickListener {
            val matricula = editTextMatricula.text.toString()
            val senha = editTextSenha.text.toString()

            val senhaSalva = prefs.getString(matricula, null)

            if (senhaSalva != null && senhaSalva == senha) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Exemplo: ir para a tela principal
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login ou senha incorretos!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCriarConta.setOnClickListener {
            val matricula = editTextMatricula.text.toString()
            val senha = editTextSenha.text.toString()

            if (matricula.isBlank() || senha.isBlank()) {
                Toast.makeText(this, "Preencha matrícula e senha", Toast.LENGTH_SHORT).show()
            } else if (prefs.contains(matricula)) {
                Toast.makeText(this, "Conta já existe!", Toast.LENGTH_SHORT).show()
            } else {
                prefs.edit().putString(matricula, senha).apply()
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}