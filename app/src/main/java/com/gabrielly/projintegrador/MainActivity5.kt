package com.gabrielly.projintegrador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter

class MainActivity5 : AppCompatActivity() {

    private lateinit var textoEmocaoRegistrada: TextView
    private lateinit var spinnerEmocoes: Spinner
    private lateinit var botaoSalvar: Button

    private val PREFS_NAME = "emocao_prefs"
    private val KEY_EMOCOES = "emocoes_registradas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        textoEmocaoRegistrada = findViewById(R.id.textoEmocaoRegistrada)
        spinnerEmocoes = Spinner(this) // Se ainda não estiver no layout, ajuste isso no XML
        botaoSalvar = Button(this)     // Certifique-se de que está no layout e com ID correto

        val emocoes = listOf(
            "Feliz", "Triste", "Bravo", "Assustado",
            "Neutro", "Confuso", "Cansado", "Doente",
            "Confiante", "Animado"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, emocoes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEmocoes.adapter = adapter

        val emocaoRecebida = intent.getStringExtra("EMOCAO")
        if (emocaoRecebida != null) {
            val posicao = emocoes.indexOf(emocaoRecebida)
            if (posicao >= 0) spinnerEmocoes.setSelection(posicao)
            adicionarEmocao(emocaoRecebida)
        }

        val botaoIrParaMainActivity5: Button = findViewById(R.id.botaoIrParaMainActivity5)
        botaoIrParaMainActivity5.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }


        atualizarTextoEmocoes()

        botaoSalvar.setOnClickListener {
            val emocaoSelecionada = spinnerEmocoes.selectedItem as String
            adicionarEmocao(emocaoSelecionada)
            atualizarTextoEmocoes()
            Toast.makeText(this, "Emoção salva com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun adicionarEmocao(emocao: String) {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val emocoesSalvas = prefs.getStringSet(KEY_EMOCOES, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        emocoesSalvas.add("${emocao} - ${obterDataHoraAtual()}")
        prefs.edit().putStringSet(KEY_EMOCOES, emocoesSalvas).apply()
    }

    private fun atualizarTextoEmocoes() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val emocoesSalvas = prefs.getStringSet(KEY_EMOCOES, setOf()) ?: setOf()
        if (emocoesSalvas.isNotEmpty()) {
            val texto = emocoesSalvas.sorted().joinToString("\n")
            textoEmocaoRegistrada.text = "Emoções registradas:\n\n$texto"
        } else {
            textoEmocaoRegistrada.text = "Nenhuma emoção registrada ainda."
        }
    }

    private fun obterDataHoraAtual(): String {
        val agora = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        return agora.format(java.util.Date())
    }
}
