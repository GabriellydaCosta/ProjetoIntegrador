package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_mood -> {
                    startActivity(Intent(this, MainActivity2::class.java))
                }
                R.id.nav_historico -> {
                    startActivity(Intent(this, MainActivity5::class.java))
                }
                R.id.nav_cadastro -> {
                    startActivity(Intent(this, MainActivity6::class.java))
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, MainActivity4::class.java))
                }
                R.id.nav_areaprofessor -> {
                    startActivity(Intent(this, MainActivity3::class.java))
                }
                R.id.nav_sair -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Opção inválida", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
