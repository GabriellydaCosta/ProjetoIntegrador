package com.gabrielly.projintegrador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baseLayout = LayoutInflater.from(this)
            .inflate(R.layout.activity_base, findViewById(android.R.id.content), false)
        setContentView(baseLayout)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_inicio -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.nav_perfil -> {
                startActivity(Intent(this, MainActivity6::class.java))
            }
            R.id.nav_mood -> {
                startActivity(Intent(this, MainActivity4::class.java))
            }
        }
        drawerLayout.closeDrawers()
        return true
    }

    fun setActivityLayout(layoutResId: Int) {
        val frame = findViewById<FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(layoutResId, frame, true)
    }
}
