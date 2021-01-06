package com.example.divar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.divar.fragment.AdListFragment
import com.example.divar.fragment.ChatFragment
import com.example.divar.fragment.FavoriteFragment
import com.example.divar.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation_with_fab.*
import kotlinx.android.synthetic.main.fab_subfab_menu.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //بک گراند fab خالی میشه -> زیر fab روی باتن نویگیشن به حالت نیم دایره درمیاد
        bottomNavigationView.background = null

        image_filter.setOnClickListener {
            drawer_layout.openDrawer(nav_view)
        }

        nav_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.main_menu -> {
                    /*      toolbar.title = getString(R.string.inbox)
                          navigationPosition = R.id.navItemInbox
                          navigateToFragment(InboxFragment.newInstance())*/
                }
                R.id.favorite -> {
                    Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show()
                }
            }
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()
            true
        }
        /*=====================Switch between Fragments in BottomNavigationView==================*/
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, ProfileFragment())
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
            }
            true
        }
        /*======================Sub Open FloatingActionButton =================================*/
        fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
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