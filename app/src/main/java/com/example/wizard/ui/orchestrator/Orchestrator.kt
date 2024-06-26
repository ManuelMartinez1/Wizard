package com.example.wizard.ui.orchestrator

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.wizard.R
import com.example.wizard.ui.add.AddFragment
import com.example.wizard.ui.home.HomeFragment
import com.example.wizard.ui.notifications.NotificationsFragment
import com.example.wizard.ui.profile.ProfileFragment
import com.example.wizard.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Orchestrator : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_orchestrator)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val menu = bottomNavigationView.menu
        val menuItem = menu.findItem(R.id.plus)

        // Aplicar un filtro de color al ícono "plus"
        menuItem.icon?.setColorFilter(ContextCompat.getColor(this, R.color.purple), PorterDuff.Mode.SRC_IN)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.search -> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.plus -> {
                    val plusIcon = menuItem.icon

                    // Aplica el filtro de color solo al ícono "plus"
                    plusIcon?.setColorFilter(ContextCompat.getColor(this, R.color.purple), PorterDuff.Mode.SRC_IN)

                    val addFragment = AddFragment()

                    // Crear un Bundle y pasar argumentos
                    val bundle = Bundle()
                    bundle.putString("key", "value")
                    addFragment.arguments = bundle

                    // Reemplazar el fragmento actual con AddFragment
                    replaceFragment(addFragment)
                    true
                }

                R.id.notifications -> {
                    replaceFragment(NotificationsFragment())
                    true
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}