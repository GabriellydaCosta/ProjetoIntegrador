package com.gabrielly.projintegrador

import android.view.MenuItem
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.gabrielly.projintegrador.databinding.ActivityMain6Binding
import com.google.gson.Gson

class MainActivity6 : AppCompatActivity() {

    private lateinit var binding: ActivityMain6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("dadosAluno", MODE_PRIVATE)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.btnSalvar.setOnClickListener {
            val editor = prefs.edit()
            editor.putString("nome", binding.edtNome.text.toString())
            editor.putString("idade", binding.edtIdade.text.toString())
            editor.putString("responsavel", binding.edtResponsavel.text.toString())
            editor.putString("sala", binding.edtSala.text.toString())
            editor.putString("turno", binding.edtTurno.text.toString())
            editor.putString("telefoneAluno", binding.edtTelefoneAluno.text.toString())
            editor.putString("telefoneResponsavel", binding.edtTelefoneResponsavel.text.toString())
            editor.apply()

            val novoAluno = Aluno(
                nome = binding.edtNome.text.toString(),
                idade = binding.edtIdade.text.toString().toIntOrNull() ?: 0,
                avatarResId = R.drawable.avatar_aluno,
                responsavel = binding.edtResponsavel.text.toString(),
                sala = binding.edtSala.text.toString(),
                turno = binding.edtTurno.text.toString(),
                telefoneAluno = binding.edtTelefoneAluno.text.toString(),
                telefoneResponsavel = binding.edtTelefoneResponsavel.text.toString()
            )

            salvarAlunoNaLista()

            val gson = Gson()
            val alunoJson = gson.toJson(novoAluno)

            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("alunoJson", alunoJson)
            startActivity(intent)
            finish()
        }


    }

    private fun salvarAlunoNaLista() {
        val sharedPrefs = getSharedPreferences("alunosCadastrados", MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val nome = binding.edtNome.text.toString()
        val idade = binding.edtIdade.text.toString().toIntOrNull() ?: 0
        val responsavel = binding.edtResponsavel.text.toString()
        val sala = binding.edtSala.text.toString()
        val turno = binding.edtTurno.text.toString()
        val telefoneAluno = binding.edtTelefoneAluno.text.toString()
        val telefoneResponsavel = binding.edtTelefoneResponsavel.text.toString()

        val novoAluno = Aluno(
            nome = nome,
            idade = idade,
            avatarResId = R.drawable.avatar_aluno, // substitua pelo ícone padrão desejado
            responsavel = responsavel,
            sala = sala,
            turno = turno,
            telefoneAluno = telefoneAluno,
            telefoneResponsavel = telefoneResponsavel
        )

        // Serializa o aluno para JSON
        val gson = com.google.gson.Gson()
        val jsonAluno = gson.toJson(novoAluno)

        // Recupera a lista existente
        val alunosJson = sharedPrefs.getStringSet("listaAlunos", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        alunosJson.add(jsonAluno)

        editor.putStringSet("listaAlunos", alunosJson)
        editor.apply()
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
