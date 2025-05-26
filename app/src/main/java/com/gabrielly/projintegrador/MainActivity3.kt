package com.gabrielly.projintegrador

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val recyclerAlunos = findViewById<RecyclerView>(R.id.recyclerAlunos)
        recyclerAlunos.layoutManager = GridLayoutManager(this, 3)
    }
}
