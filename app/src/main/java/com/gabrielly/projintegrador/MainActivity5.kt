package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import android.view.Menu
import android.view.MenuItem
import java.util.*

class MainActivity5 : AppCompatActivity() {

    private lateinit var textoEmocaoRegistrada: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Histórico de emoções"

        textoEmocaoRegistrada = findViewById(R.id.textoEmocaoRegistrada)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        carregarRegistrosUltimos7Dias()
    }

    private fun carregarRegistrosUltimos7Dias() {
        val usuario = auth.currentUser
        if (usuario == null) {
            textoEmocaoRegistrada.text = "Usuário não autenticado."
            return
        }

        val uid = usuario.uid
        val emocaoRef = database.child("users").child(uid).child("historicoEmocoes")

        // Timestamp de 7 dias atrás em milissegundos
        val seteDiasEmMillis = 7L * 24 * 60 * 60 * 1000
        val timestampLimite = System.currentTimeMillis() - seteDiasEmMillis

        // Consulta para pegar apenas registros com timestamp >= timestampLimite
        emocaoRef.orderByKey().startAt(timestampLimite.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        textoEmocaoRegistrada.text = "Nenhum registro dos últimos 7 dias."
                        return
                    }

                    val builder = StringBuilder()
                    for (registro in snapshot.children) {
                        val emocao = registro.child("emocao").getValue(String::class.java)
                        val dataHora = registro.child("dataHora").getValue(String::class.java)
                        if (emocao != null && dataHora != null) {
                            builder.append("• $dataHora - $emocao\n")
                        }
                    }
                    textoEmocaoRegistrada.text = builder.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    textoEmocaoRegistrada.text = "Erro ao carregar: ${error.message}"
                }
            })
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
