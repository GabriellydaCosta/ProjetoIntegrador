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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Perfil"

        carregarPerfilAlunoFirebase()
    }

    private fun carregarPerfilAlunoFirebase() {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null) {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = usuario.uid
        val database = FirebaseDatabase.getInstance().reference

        database.child("users").child(uid).child("perfilAluno")
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
        // Se quiser mostrar avatar, use o campo avatarResId
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
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
