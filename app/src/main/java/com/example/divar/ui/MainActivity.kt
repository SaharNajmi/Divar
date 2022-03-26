package com.example.divar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.divar.R
import com.example.divar.ui.add.AddFragment
import com.example.divar.ui.auth.LoginFragment
import com.example.divar.ui.favorite.FavoriteFragment
import com.example.divar.ui.home.AdListFragment
import com.example.divar.ui.message.ChatFragment
import kotlinx.android.synthetic.main.bottom_navigation_with_fab.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //BottomNavigationView
        buttonNavigation()
    }

    private fun buttonNavigation() {
        //empty background fab
        bottomNavigationView.background = null

        //show default fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.switch_fragment, AdListFragment())
            .commit()

        //bottom navigation default selected option
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, LoginFragment())
                        .commit()
                }
                R.id.chat -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.switch_fragment, ChatFragment())
                        .commit()
                }
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, AdListFragment())
                        .commit()
                }
                R.id.favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, FavoriteFragment())
                        .commit()
                }
                R.id.add -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, AddFragment())
                        .commit()
                }
            }
            true
        }
    }
}