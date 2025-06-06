package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielly.projintegrador.databinding.ActivityMain6Binding

class MainActivity6 : AppCompatActivity() {

    private lateinit var binding: ActivityMain6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("dadosAluno", MODE_PRIVATE)

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

            startActivity(Intent(this, MainActivity4::class.java))
            finish()
        }
    }
}
