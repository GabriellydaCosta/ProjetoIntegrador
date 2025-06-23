package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private var emojiSelecionado: String? = null
    private lateinit var botaoConfirmar: Button
    private lateinit var botaoRegistros: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Registro de Emoção"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        botaoConfirmar = findViewById(R.id.buttonConfirmar)
        botaoRegistros = findViewById(R.id.buttonRegistros)

        val emojis = mapOf(
            R.id.emojiFeliz to "Feliz",
            R.id.emojiTriste to "Triste",
            R.id.emojiBravo to "Bravo",
            R.id.emojiAssustado to "Assustado",
            R.id.emojiNeutro to "Neutro",
            R.id.emojiConfuso to "Confuso",
            R.id.emojiCansado to "Cansado",
            R.id.emojiDoente to "Doente",
            R.id.emojiConfiante to "Confiante",
            R.id.emojiAnimado to "Animado"
        )

        for ((id, emocao) in emojis) {
            findViewById<ImageButton>(id).setOnClickListener {
                emojiSelecionado = emocao
                Toast.makeText(this, "Selecionado: $emocao", Toast.LENGTH_SHORT).show()
            }
        }

        botaoConfirmar.setOnClickListener {
            val usuario = auth.currentUser

            if (emojiSelecionado == null) {
                Toast.makeText(this, "Por favor, selecione uma emoção.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuario == null) {
                Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = usuario.uid
            val timestamp = System.currentTimeMillis()
            val dataHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            val emocaoMap = mapOf(
                "emocao" to emojiSelecionado,
                "dataHora" to dataHora
            )

            database.child("users").child(uid).child("historicoEmocoes").child(timestamp.toString())
                .setValue(emocaoMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Emoção registrada!", Toast.LENGTH_SHORT).show()

                    // Inicia a Activity5 passando o timestamp do registro
                    val intent = Intent(this, MainActivity5::class.java)
                    intent.putExtra("timestamp", timestamp.toString())
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        botaoRegistros.setOnClickListener {
            startActivity(Intent(this, MainActivity5::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_mood -> {
                startActivity(Intent(this, MainActivity2::class.java))
                true
            }

            R.id.nav_perfil -> {
                startActivity(Intent(this, MainActivity4::class.java))
                true
            }

            R.id.nav_historico -> {
                startActivity(Intent(this, MainActivity5::class.java))
                true
            }

            R.id.nav_cadastro -> {
                startActivity(Intent(this, MainActivity6::class.java))
                true
            }

            R.id.nav_areaprofessor -> {
                startActivity(Intent(this, MainActivity3::class.java))
                true
            }

            R.id.nav_sair -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Você saiu", Toast.LENGTH_SHORT).show()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
