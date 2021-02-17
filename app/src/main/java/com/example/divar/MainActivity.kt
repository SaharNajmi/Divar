package com.example.divar

import adapter.ExpandableListCategoryAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation_with_fab.*
import kotlinx.android.synthetic.main.fab_subfab_menu.*
import kotlinx.android.synthetic.main.nav_header.view.*
import view.*


class MainActivity : AppCompatActivity() {

    var isFABOpen = false

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    private val cate_base = HashMap<String, List<String>>()
    private val cityArray = listOf("کردستان", "تهران", "اردبیل")

    private var myDataSaved: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private val data: HashMap<String, List<String>>
        get() {
            val cate_0 = ArrayList<String>()
            cate_0.add("موبایل")
            cate_0.add("تبلت")
            cate_0.add("لپ تاپ")

            val cate_1 = ArrayList<String>()
            cate_1.add("رهن و اجاره")
            cate_1.add("خرید و فروش")

            val cate_2 = ArrayList<String>()
            cate_2.add("خودرو")
            cate_2.add("موتور سیکلت")
            cate_2.add("اجاره خودرو")
            cate_2.add("کشاورزی")

            cate_base["لوازم الکترونیکی"] = cate_0
            cate_base["املاک"] = cate_1
            cate_base["وسایل نقلیه"] = cate_2

            return cate_base
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //بک گراند fab خالی میشه -> زیر fab روی باتن نویگیشن به حالت نیم دایره درمیاد
        bottomNavigationView.background = null

        /*===================================== Navigation Drawer ==================================*/
        image_filter.setOnClickListener {
            drawer_layout.openDrawer(linearLayout)
        }

        nav_view.setNavigationItemSelectedListener { menuItem ->
            drawer_layout.openDrawer(nav_view)
            when (menuItem.itemId) {
                R.id.main_menu -> {

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

        /*========================expandableListView  <list and sublist category>==================*/
        expandableListView = nav_view.getHeaderView(0).expandableListViewHeader

        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = ExpandableListCategoryAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " باز شد ",
                    Toast.LENGTH_SHORT
                ).show()
            }

            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " بسته شد ",
                    Toast.LENGTH_SHORT
                ).show()
            }

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

                val category = "$groupPosition,$childPosition"
                val titleCate =
                    (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!![childPosition]
                val go = Intent(this, CategoryActivity::class.java)
                go.putExtra("category", category)
                go.putExtra("titleCate", titleCate)
                startActivity(go)

                Toast.makeText(
                    applicationContext,
                    "clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }
        /*   expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
               Toast.makeText(
                   applicationContext,
                   "clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + cate_base[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                       childPosition
                   ),
                   Toast.LENGTH_SHORT
               ).show()
               false
           }*/

        /*=====================Switch between Fragments in BottomNavigationView==================*/

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
                R.id.favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.switch_fragment, FavoriteFragment())
                        .commit()
                }
            }
            true
        }

        floatingAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.switch_fragment, NewAdFragment())
                .commit()
        }

        /*======================Sub Open FloatingActionButton =================================*/
        fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

        /*==================================spinner city======================================*/
        /*  val adapterCity = ArrayAdapter<String>(this,
              R.layout.row_spinner, cityArray)
          spinner_city.adapter = adapterCity

          myDataSaved = getSharedPreferences("myCity", Context.MODE_PRIVATE)
          val cityCode = myDataSaved?.getInt("spinnerSelectionCity", 0)
          spinner_city.setSelection(cityCode!!)

          spinner_city.setOnItemSelectedListener(object : OnItemSelectedListener {
              override fun onItemSelected(
                  parentView: AdapterView<*>?,
                  selectedItemView: View?,
                  position: Int,
                  id: Long
              ) {
                  val selectedPosition: Int = spinner_city.selectedItemPosition
                  val cityName=cityArray[cityCode]

                  editor = myDataSaved?.edit()
                  editor?.putInt("spinnerSelectionCity", selectedPosition)
                  editor?.putString("CityName", cityName)
                  editor?.apply()
              }

              override fun onNothingSelected(parentView: AdapterView<*>?) {}
          })*/
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