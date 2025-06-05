package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import com.gabrielly.projintegrador.databinding.ActivityMain6Binding

class MainActivity6 : BaseActivity() {

    private lateinit var binding: ActivityMain6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityLayout(R.layout.activity_main6) // inflar o conteúdo no Drawer

        binding = ActivityMain6Binding.bind(findViewById(R.id.content_frame)) // usa o conteúdo dentro do frame

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
