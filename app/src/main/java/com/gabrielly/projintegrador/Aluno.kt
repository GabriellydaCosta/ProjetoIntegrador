package com.gabrielly.projintegrador

data class Aluno(
    var uid: String? = null,          // Novo campo para guardar UID
    var nome: String = "",
    var idade: Int = 0,
    var avatarResId: Int = 0,
    var responsavel: String = "",
    var sala: String = "",
    var turno: String = "",
    var telefoneAluno: String = "",
    var telefoneResponsavel: String = ""
)
