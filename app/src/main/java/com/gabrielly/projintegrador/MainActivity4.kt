package com.gabrielly.projintegrador

import android.content.Intent
import android.view.MenuItem
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielly.projintegrador.databinding.ActivityMain4Binding
import com.google.gson.Gson

class MainActivity4 : AppCompatActivity() {

    private lateinit var binding: ActivityMain4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("dadosAluno", MODE_PRIVATE)


        val nome = prefs.getString("nome", "Sem nome")
        val idade = prefs.getString("idade", "Sem idade")
        val responsavel = prefs.getString("responsavel", "Sem responsável")
        val sala = prefs.getString("sala", "Sem sala")
        val turno = prefs.getString("turno", "Sem turno")
        val telefoneAluno = prefs.getString("telefoneAluno", "Sem telefone")
        val telefoneResponsavel = prefs.getString("telefoneResponsavel", "Sem telefone")

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Exemplo: exibindo nos TextViews
        val alunoJson = intent.getStringExtra("alunoJson")
        if (alunoJson != null) {
            val gson = Gson()
            val aluno = gson.fromJson(alunoJson, Aluno::class.java)

            binding.txtNome.text = "Nome: ${aluno.nome}"
            binding.txtIdade.text = "Idade: ${aluno.idade}"
            binding.txtResponsavel.text = "Responsável: ${aluno.responsavel}"
            binding.txtSala.text = "Sala: ${aluno.sala}"
            binding.txtTurno.text = "Turno: ${aluno.turno}"
            binding.txtTelefoneAluno.text = "Telefone Aluno: ${aluno.telefoneAluno}"
            binding.txtTelefoneResponsavel.text = "Telefone Responsável: ${aluno.telefoneResponsavel}"
        }

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
