package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabrielly.projintegrador.databinding.ActivityMain4Binding
import com.google.gson.Gson

class MainActivity4 : AppCompatActivity() {

    private lateinit var binding: ActivityMain4Binding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // ✅ Verifica se foi passado aluno via Intent (caso vindo do professor)
        val alunoJsonIntent = intent.getStringExtra("alunoJson")
        if (alunoJsonIntent != null) {
            val aluno = gson.fromJson(alunoJsonIntent, Aluno::class.java)
            exibirDadosAluno(aluno)
        } else {
            // ✅ Caso contrário, mostra o aluno logado normalmente
            mostrarAlunoDoUsuarioLogado()
        }
    }

    private fun mostrarAlunoDoUsuarioLogado() {
        val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val usuarioLogado = prefsLogin.getString("usuarioLogado", null)

        if (usuarioLogado == null) {
            Toast.makeText(this, "Usuário não está logado.", Toast.LENGTH_SHORT).show()
            return
        }

        val prefsAlunos = getSharedPreferences("alunosPorUsuario", MODE_PRIVATE)
        val alunoJson = prefsAlunos.getString(usuarioLogado, null)

        if (alunoJson == null) {
            Toast.makeText(this, "Nenhum aluno cadastrado para este usuário.", Toast.LENGTH_SHORT).show()
            return
        }

        val aluno = gson.fromJson(alunoJson, Aluno::class.java)
        exibirDadosAluno(aluno)
    }

    private fun exibirDadosAluno(aluno: Aluno) {
        binding.txtNome.text = "Nome: ${aluno.nome}"
        binding.txtIdade.text = "Idade: ${aluno.idade}"
        binding.txtResponsavel.text = "Responsável: ${aluno.responsavel}"
        binding.txtSala.text = "Sala: ${aluno.sala}"
        binding.txtTurno.text = "Turno: ${aluno.turno}"
        binding.txtTelefoneAluno.text = "Telefone Aluno: ${aluno.telefoneAluno}"
        binding.txtTelefoneResponsavel.text = "Telefone Responsável: ${aluno.telefoneResponsavel}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mood -> {
                startActivity(Intent(this, MainActivity2::class.java))
                return true
            }
            R.id.nav_perfil -> {
                startActivity(Intent(this, MainActivity4::class.java))
                return true
            }
            R.id.nav_historico -> {
                startActivity(Intent(this, MainActivity5::class.java))
                return true
            }
            R.id.nav_cadastro -> {
                startActivity(Intent(this, MainActivity6::class.java))
                return true
            }
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
}
