package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity3 : AppCompatActivity() {

    private lateinit var recyclerAlunos: RecyclerView
    private lateinit var adapter: AlunoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔒 Verifica se o usuário logado é um professor
        val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val tipoUsuario = prefsLogin.getString("tipoUsuario", null)

        if (tipoUsuario != "professor") {
            Toast.makeText(this, "Acesso restrito ao professor.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main3)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerAlunos = findViewById(R.id.recyclerAlunos)
        recyclerAlunos.layoutManager = GridLayoutManager(this, 3)

        carregarAlunos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val tipoUsuario = prefsLogin.getString("tipoUsuario", null)

        if (tipoUsuario == "professor") {
            // Oculta todas as opções exceto "Área do Professor" e "Sair"
            menu?.findItem(R.id.nav_mood)?.isVisible = false
            menu?.findItem(R.id.nav_perfil)?.isVisible = false
            menu?.findItem(R.id.nav_historico)?.isVisible = false
            menu?.findItem(R.id.nav_cadastro)?.isVisible = false
            menu?.findItem(R.id.nav_areaprofessor)?.isVisible = true
            menu?.findItem(R.id.nav_sair)?.isVisible = true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_areaprofessor -> {
                startActivity(Intent(this, MainActivity3::class.java))
                return true
            }

            R.id.nav_sair -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
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

