package com.example.divar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {


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
    }
}