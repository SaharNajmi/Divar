package com.example.divar.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import com.example.divar.common.*
import com.example.divar.common.Constants.EXTRA_KEY_DATA
import com.example.divar.common.Constants.LIST_CITY
import com.example.divar.common.Constants.MY_CATEGORY
import com.example.divar.common.Constants.MY_CITY
import com.example.divar.data.db.dao.entities.Advertise
import kotlinx.android.synthetic.main.fragment_ad_list.*
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AdListFragment : MyFragment(), ItemOnClickListener {

    lateinit var bannerAdapter: BannerAdapter

    private val bannerViewModel: BannerViewModel by viewModel<BannerViewModel>() {
        parametersOf(
            MY_CITY,
            MY_CATEGORY
        )
    }
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titles: List<String>? = null
    private val cate_base = HashMap<String, List<String>>()
    private val cate_sub: HashMap<String, List<String>>
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //show banner
        getBanners()

        //search list city with AutoCompleteTextView
        searchCity()

        //search banner
        searchView()

        //change category and list update
        selectSubCategory()

        //show or not show ProgressBar
        bannerViewModel.progress.observe(viewLifecycleOwner) {
            setProgress(it)
        }
    }

    private fun getBanners() {
        bannerViewModel.banners.observe(viewLifecycleOwner,
            object : Observer<List<Advertise>> {
                override fun onChanged(banners: List<Advertise>?) {
                    //  Timber.i("my list " + banners.toString())

                    if (banners!!.isNotEmpty()) {
                        bannerAdapter =
                            BannerAdapter(banners as ArrayList<Advertise>, this@AdListFragment)
                        val manager =
                            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

                        rec_ad.layoutManager = manager
                        rec_ad.adapter = bannerAdapter

                        //update list
                        bannerAdapter.setData(banners as ArrayList<Advertise>)

                        rec_ad.visibility = View.VISIBLE
                        emptyLayout.visibility = View.GONE

                    } else {
                        emptyLayout.visibility = View.VISIBLE
                        emptyLayout.txtEmpty.text = getString(R.string.emptyList)
                    }

                }
            })
    }

    private fun searchCity() {
        //filter advertise by city
        val list = LIST_CITY
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, list)
        auto_complete_textView.setAdapter(adapter)
        auto_complete_textView.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {

                //empty text in auto_complete_textView when click and equals text=all city
                if (!auto_complete_textView.equals(R.string.all_city)) {
                    auto_complete_textView.setText("")
                }

                //do not select city or enter wrong city
                if (!hasFocus) {
                    if (!list.contains(auto_complete_textView.text.toString())) {
                        auto_complete_textView.setText(R.string.all_city)
                        MY_CITY = "all"
                        chaneCity()
                    }
                }
            }
        })

        //update list advertise by city selected
        auto_complete_textView.setOnItemClickListener(OnItemClickListener { arg0, arg1, arg2, arg3 ->
            MY_CITY = auto_complete_textView.text.toString().trim()
            chaneCity()
        })
    }

    private fun chaneCity() {
        //update list
        bannerViewModel.changeCity(MY_CITY)
    }

    private fun chaneCategory() {
        //update list
        bannerViewModel.chaneCategory(MY_CATEGORY)
    }

    private fun searchView() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bannerAdapter.filter.filter(newText)
                return true
            }
        })
    }

    fun selectSubCategory() {
        //navigation view
        imageFilter.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        //expandableListView  <list and sublist category>
        expandableListView = navView.getHeaderView(0).expandableListViewHeader

        if (expandableListView != null) {
            val listData = cate_sub
            titles = ArrayList(listData.keys)
            adapter = ExpandableListCategoryAdapter(
                requireContext(),
                titles as ArrayList<String>,
                listData
            )
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                val groupP = groupPosition + 1
                val childP = childPosition + 1
                MY_CATEGORY = "$groupP,$childP"

                //show title category in textView
                val titleCate =
                    listData[(titles as ArrayList<String>)[groupPosition]]!![childPosition]
                textFilter.text = titleCate

                //delete filter
                if (textFilter.text.equals(R.string.filterName))
                    cancel_filter.visibility = View.GONE
                else
                    cancel_filter.visibility = View.VISIBLE

                cancel_filter.setOnClickListener {
                    cancel_filter.visibility = View.GONE
                    textFilter.setText(R.string.filterName)
                    MY_CATEGORY = "all"
                    chaneCategory()
                }

                //update list by category
                chaneCategory()

                drawerLayout.closeDrawer(navView)

                false
            }
        }
    }

    override fun onItemClick(item: Advertise) {
        startActivity(Intent(requireContext(), DetailAdActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, item)
        })
    }

    override fun onResume() {
        super.onResume()
        bannerViewModel.refresh()
    }
}