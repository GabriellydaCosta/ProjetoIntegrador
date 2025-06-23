package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {

    private lateinit var recyclerAlunos: RecyclerView
    private lateinit var adapter: AlunoAdapter
    private val listaAlunos = mutableListOf<Aluno>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica se o usuário é professor (proteção extra)
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

        carregarAlunosFirebase()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        val prefsLogin = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val tipoUsuario = prefsLogin.getString("tipoUsuario", null)

        if (tipoUsuario == "professor") {
            // Deixa visíveis só as opções necessárias para professor
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
                // Se já está na área do professor, apenas avisa
                Toast.makeText(this, "Você já está na área do professor.", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_sair -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun carregarAlunosFirebase() {
        val database = FirebaseDatabase.getInstance().reference.child("users")
        listaAlunos.clear()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaAlunos.clear()
                for (userSnapshot in snapshot.children) {
                    val aluno = userSnapshot.child("perfilAluno").getValue(Aluno::class.java)
                    if (aluno != null) {
                        // Aqui se quiser, pode guardar o UID no objeto Aluno para passar depois
                        // Exemplo: aluno.uid = userSnapshot.key
                        listaAlunos.add(aluno)
                    }
                }
                adapter = AlunoAdapter(listaAlunos) { alunoSelecionado ->
                    val intent = Intent(this@MainActivity3, MainActivity4::class.java)
                    // Se quiser passar o UID, faça isso aqui
                    // Exemplo: intent.putExtra("uidAluno", alunoSelecionado.uid)
                    startActivity(intent)
                }
                recyclerAlunos.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity3, "Erro ao carregar alunos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
