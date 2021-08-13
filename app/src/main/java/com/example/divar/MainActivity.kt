package com.example.divar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bottom_navigation_with_fab.*
import kotlinx.android.synthetic.main.fab_subfab_menu.*
import ui.add.NewAdFragment
import ui.auth.LoginFragment
import ui.home.AdListFragment
import ui.message.ChatFragment

class MainActivity : AppCompatActivity() {

    var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Switch between Fragments in BottomNavigationView
        buttonNavigation()

        //Sub Open FloatingActionButton
        fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

    }

    fun buttonNavigation() {
        //بک گراند fab خالی میشه -> زیRelativeLayoutر fab روی باتن نویگیشن به حالت نیم دایره درمیاد
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
                    /*     val pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
                         if (pref.getBoolean("login", false)) {
                             supportFragmentManager.beginTransaction()
                                 .replace(R.id.switch_fragment, ProfileFragment())
                                 .commit()
                         } else {
                             supportFragmentManager.beginTransaction()
                                 .replace(R.id.switch_fragment, LoginFragment())
                                 .commit()
                         }*/
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
             /*   R.id.favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, FavoriteFragment())
                        .commit()
                }*/
                R.id.add -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, NewAdFragment())
                        .commit()
                }
            }
            true
        }
    }

    private fun showFABMenu() {
        isFABOpen = true
        fab1.animate().translationY(-90F)
        fab2.animate().translationY(-180F)
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fab1.animate().translationY(0F)
        fab2.animate().translationY(0F)
    }
}