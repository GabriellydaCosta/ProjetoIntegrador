package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gabrielly.projintegrador.databinding.ActivityMain4Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.view.Menu
import android.view.MenuItem

class MainActivity4 : AppCompatActivity() {

    private lateinit var binding: ActivityMain4Binding
    private var ehProfessor: Boolean = false  // Novo: define se é professor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Perfil"

        val uidAlunoIntent = intent.getStringExtra("uidAluno")
        val uidUsuarioAtual = FirebaseAuth.getInstance().currentUser?.uid

        // Detecta se é professor (se o UID do aluno é diferente do usuário logado)
        ehProfessor = uidAlunoIntent != null && uidAlunoIntent != uidUsuarioAtual

        carregarPerfilAlunoFirebase(uidAlunoIntent ?: uidUsuarioAtual)
    }

    private fun carregarPerfilAlunoFirebase(uidAluno: String?) {
        if (uidAluno == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val database = FirebaseDatabase.getInstance().reference

        database.child("users").child(uidAluno).child("perfilAluno")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val aluno = snapshot.getValue(Aluno::class.java)
                    if (aluno != null) {
                        exibirDadosAluno(aluno)
                    } else {
                        Toast.makeText(this@MainActivity4, "Nenhum perfil cadastrado.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity4, "Erro ao carregar perfil: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
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
        if (ehProfessor) {
            menuInflater.inflate(R.menu.menu_professor, menu)  // novo menu
        } else {
            menuInflater.inflate(R.menu.menu_principal, menu) // menu do aluno
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_mood -> {
                if (!ehProfessor) startActivity(Intent(this, MainActivity2::class.java))
                true
            }
            R.id.nav_perfil -> {
                if (!ehProfessor) startActivity(Intent(this, MainActivity4::class.java))
                true
            }
            R.id.nav_historico -> {
                if (!ehProfessor) startActivity(Intent(this, MainActivity5::class.java))
                true
            }
            R.id.nav_cadastro -> {
                if (!ehProfessor) startActivity(Intent(this, MainActivity6::class.java))
                true
            }
            R.id.nav_areaprofessor -> {
                startActivity(Intent(this, MainActivity3::class.java))
                true
            }
            R.id.nav_sair -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Você saiu", Toast.LENGTH_SHORT).show()

                // Redireciona para a tela de login e limpa o histórico
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
