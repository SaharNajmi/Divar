package com.example.divar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.divar.adapter.PageAdapter
import com.example.divar.fragment.AdListFragment
import com.example.divar.fragment.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fab_subfab_menu.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    //  Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show()
                }
                R.id.chat -> {
                    Toast.makeText(this, "aaaaaaaaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show()
                }
                R.id.home -> {
                    Toast.makeText(this, "aaaaaaaaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show()
                }
                R.id.favorite -> {
                    val go = Intent(this, FavoriteFragment::class.java)
                    startActivity(go)
                    viewPager.currentItem = 3
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