package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    // Variável para armazenar o emoji selecionado (opcional)
    private var emojiSelecionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Referências dos ImageButtons dos emojis
        val emojiFeliz = findViewById<ImageButton>(R.id.emojiFeliz)
        val emojiTriste = findViewById<ImageButton>(R.id.emojiTriste)
        val emojiBravo = findViewById<ImageButton>(R.id.emojiBravo)
        val emojiAssustado = findViewById<ImageButton>(R.id.emojiAssustado)
        val emojiNeutro = findViewById<ImageButton>(R.id.emojiNeutro)
        val emojiConfuso = findViewById<ImageButton>(R.id.emojiConfuso)
        val emojiCansado = findViewById<ImageButton>(R.id.emojiCansado)
        val emojiDoente = findViewById<ImageButton>(R.id.emojiDoente)
        val emojiConfiante = findViewById<ImageButton>(R.id.emojiConfiante)
        val emojiAnimado = findViewById<ImageButton>(R.id.emojiAnimado)

        val botaoIrParaMainActivity5: Button = findViewById(R.id.buttonRegistros)
        botaoIrParaMainActivity5.setOnClickListener {
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }



        // Botão confirmar
        val buttonConfirmar = findViewById<Button>(R.id.buttonConfirmar)

        // Setar click listener em cada emoji para guardar a emoção selecionada
        emojiFeliz.setOnClickListener { emojiSelecionado = "Feliz" }
        emojiTriste.setOnClickListener { emojiSelecionado = "Triste" }
        emojiBravo.setOnClickListener { emojiSelecionado = "Bravo" }
        emojiAssustado.setOnClickListener { emojiSelecionado = "Assustado" }
        emojiNeutro.setOnClickListener { emojiSelecionado = "Neutro" }
        emojiConfuso.setOnClickListener { emojiSelecionado = "Confuso" }
        emojiCansado.setOnClickListener { emojiSelecionado = "Cansado" }
        emojiDoente.setOnClickListener { emojiSelecionado = "Doente" }
        emojiConfiante.setOnClickListener { emojiSelecionado = "Confiante" }
        emojiAnimado.setOnClickListener { emojiSelecionado = "Animado" }

        // Clique do botão confirmar: só prossegue se um emoji tiver sido selecionado
        buttonConfirmar.setOnClickListener {
            if (emojiSelecionado != null) {
                abrirRegistro(emojiSelecionado!!)
            } else {
                // Pode mostrar um Toast avisando para selecionar um emoji antes
                android.widget.Toast.makeText(this, "Selecione uma emoção antes de confirmar", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun abrirRegistro(emocao: String) {
        val intent = Intent(this, MainActivity5::class.java)
        intent.putExtra("EMOCAO", emocao)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mood -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_perfil -> {
                val intent = Intent(this, MainActivity4::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_historico -> {
                val intent = Intent(this, MainActivity5::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_cadastro -> {
                val intent = Intent(this, MainActivity6::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_areaprofessor -> {
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_sair -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Fecha a tela atual
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

