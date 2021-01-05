package com.example.divar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fab_subfab_menu.*


class TestActivity : AppCompatActivity() {

    var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        /*    bbb.setOnClickListener {
                //   drawer_layout.openDrawer(nav_view)
                val go = Intent(this, AdListFragment::class.java)
                startActivity(go)

            }*/

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