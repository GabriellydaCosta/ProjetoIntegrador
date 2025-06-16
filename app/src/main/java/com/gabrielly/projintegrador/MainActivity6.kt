package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabrielly.projintegrador.databinding.ActivityMain6Binding
import com.google.gson.Gson

class MainActivity6 : AppCompatActivity() {

    private lateinit var binding: ActivityMain6Binding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Garante que a Toolbar funcione com o menu
        setSupportActionBar(findViewById(R.id.toolbar))

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString().trim()
            val idadeStr = binding.edtIdade.text.toString().trim()
            val responsavel = binding.edtResponsavel.text.toString().trim()
            val sala = binding.edtSala.text.toString().trim()
            val turno = binding.edtTurno.text.toString().trim()
            val telefoneAluno = binding.edtTelefoneAluno.text.toString().trim()
            val telefoneResponsavel = binding.edtTelefoneResponsavel.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o nome do aluno.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idade = idadeStr.toIntOrNull() ?: 0

            val novoAluno = Aluno(
                nome = nome,
                idade = idade,
                avatarResId = R.drawable.avatar_aluno,
                responsavel = responsavel,
                sala = sala,
                turno = turno,
                telefoneAluno = telefoneAluno,
                telefoneResponsavel = telefoneResponsavel
            )

            salvarAlunoDoUsuario(novoAluno)

            val alunoJson = gson.toJson(novoAluno)
            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("alunoJson", alunoJson)
            startActivity(intent)
            finish()
        }
    }

    private fun salvarAlunoDoUsuario(aluno: Aluno) {
        val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val usuarioLogado = prefsLogin.getString("usuarioLogado", null)

        if (usuarioLogado == null) {
            Toast.makeText(this, "Usuário não está logado.", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPrefs = getSharedPreferences("alunosPorUsuario", MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val alunoJson = gson.toJson(aluno)
        editor.putString(usuarioLogado, alunoJson)
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        // ✅ Exemplo opcional: esconder Área do Professor se não for professor
        val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val tipoUsuario = prefsLogin.getString("tipoUsuario", null)
        if (tipoUsuario != "professor") {
            menu?.findItem(R.id.nav_areaprofessor)?.isVisible = false
        }

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
