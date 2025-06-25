package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gabrielly.projintegrador.databinding.ActivityMain6Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.view.Menu
import android.view.MenuItem

class MainActivity6 : AppCompatActivity() {

    private lateinit var binding: ActivityMain6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cadastro"

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

            salvarAlunoFirebase(novoAluno)
        }
    }

    private fun salvarAlunoFirebase(aluno: Aluno) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null) {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = usuario.uid
        val database = FirebaseDatabase.getInstance().reference

        // Salva ou atualiza o perfil do aluno no caminho users/{uid}/perfilAluno
        database.child("users").child(uid).child("perfilAluno")
            .setValue(aluno)
            .addOnSuccessListener {
                Toast.makeText(this, "Perfil salvo com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity4::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar: ${it.message}", Toast.LENGTH_SHORT).show()
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

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
