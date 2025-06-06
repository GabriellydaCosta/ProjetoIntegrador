package com.gabrielly.projintegrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielly.projintegrador.databinding.ActivityMain4Binding

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

        // Exemplo: exibindo nos TextViews
        binding.txtNome.text = "Nome: $nome"
        binding.txtIdade.text = "Idade: $idade"
        binding.txtResponsavel.text = "Responsável: $responsavel"
        binding.txtSala.text = "Sala: $sala"
        binding.txtTurno.text = "Turno: $turno"
        binding.txtTelefoneAluno.text = "Telefone Aluno: $telefoneAluno"
        binding.txtTelefoneResponsavel.text = "Telefone Responsável: $telefoneResponsavel"
    }
}
