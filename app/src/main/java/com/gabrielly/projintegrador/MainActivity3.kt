package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity3 : AppCompatActivity() {

    private lateinit var recyclerAlunos: RecyclerView
    private lateinit var adapter: AlunoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerAlunos = findViewById(R.id.recyclerAlunos)
        recyclerAlunos.layoutManager = GridLayoutManager(this, 3)

        carregarAlunos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mood -> startActivity(Intent(this, MainActivity2::class.java))
            R.id.nav_perfil -> startActivity(Intent(this, MainActivity4::class.java))
            R.id.nav_historico -> startActivity(Intent(this, MainActivity5::class.java))
            R.id.nav_cadastro -> startActivity(Intent(this, MainActivity6::class.java))
            R.id.nav_areaprofessor -> startActivity(Intent(this, MainActivity3::class.java))
            R.id.nav_sair -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun carregarAlunos() {
        val sharedPrefs = getSharedPreferences("alunosPorUsuario", MODE_PRIVATE)
        val allEntries = sharedPrefs.all

        val gson = Gson()
        val listaAlunos = allEntries.values.mapNotNull { value ->
            try {
                gson.fromJson(value as String, Aluno::class.java)
            } catch (e: Exception) {
                null
            }
        }

        adapter = AlunoAdapter(listaAlunos) { alunoSelecionado ->
            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("alunoJson", gson.toJson(alunoSelecionado))
            startActivity(intent)
        }

        recyclerAlunos.adapter = adapter
    }
}