package com.gabrielly.projintegrador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlunoAdapter(
    private val listaAlunos: List<Aluno>,
    private val onAlunoClick: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    inner class AlunoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Certifique-se de que os IDs abaixo existem no layout item_aluno.xml
        val imgAvatar: ImageView = itemView.findViewById(R.id.imageAluno)
        val txtNome: TextView = itemView.findViewById(R.id.textNomeAluno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aluno, parent, false)
        return AlunoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = listaAlunos[position]
        holder.txtNome.text = aluno.nome
        holder.imgAvatar.setImageResource(aluno.avatarResId)

        holder.itemView.setOnClickListener {
            onAlunoClick(aluno)
        }
    }

    override fun getItemCount(): Int = listaAlunos.size
}
