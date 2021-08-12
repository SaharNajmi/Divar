package ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import commom.*
import data.model.AdModel
import kotlinx.android.synthetic.main.fragment_ad_list.*
import kotlinx.android.synthetic.main.fragment_new_ad.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class AdListFragment : Fragment(), ItemOnClickListener {

    lateinit var bannerAdapter: BannerAdapter

    lateinit var mdrawerlayout: DrawerLayout

    //instance viewModel use koin
    val bannerViewModel: BannerViewModel by viewModel<BannerViewModel>() {
        parametersOf(
            MY_CITY,
            MY_CATEGORY
        )
    }
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
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
        /*     var myDataSaved = activity?.getSharedPreferences("myCity", Context.MODE_PRIVATE)
               val cityName = myDataSaved?.getString("CityName", "all")*/

        //show banner
        getBanner()

        //search list city with AutoCompleteTextView
        searchCity()

        //search banner
        searchView()

        //change category and list update
        selectSubCategory()
    }

    fun getBanner() {
        bannerViewModel.bannerLiveData.observe(viewLifecycleOwner,
            object : Observer<List<AdModel>> {
                override fun onChanged(banners: List<AdModel>?) {
                    Timber.i("my list " + banners.toString())

                    bannerAdapter =
                        BannerAdapter(banners as ArrayList<AdModel>, this@AdListFragment)
                    val manager =
                        GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

                    rec_ad.layoutManager = manager
                    rec_ad.adapter = bannerAdapter

                    //ست کردن آرایه جدید بعد از هر بار جستجو
                    bannerAdapter.setData(banners)
                }
            })
    }

    fun searchCity() {
        //آگهی ها را بر اساس شهر انتخاب شده نمایش دهد
        val list = LIST_CITY
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, list)
        exposed_dropdown_city.setAdapter(adapter)
        exposed_dropdown_city.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {

                //موقعی که هیچ شهری انتخاب نکند یا مقدار ورودی اشتباه باشد(داخل لیست نباشد)
                if (!hasFocus) {
                    if (!list.contains(exposed_dropdown_city.text.toString())) {
                        exposed_dropdown_city.setText("همه شهر ها")
                        MY_CITY = "all"
                        chaneCity()
                    }
                }
            }
        })

        //موقعی که روی شهر مورد نظر کلیک کرد مقدار را بگیرد-> برای آپدیت آگهی ها ب اساس شهر
        exposed_dropdown_city.setOnItemClickListener(OnItemClickListener { arg0, arg1, arg2, arg3 ->
            MY_CITY = exposed_dropdown_city.text.toString().trim()
            chaneCity()
        })

    }

    fun chaneCity() {
        //هر سری که شهر عوض شد دوباره یست آگهی ها را بگیرد
        bannerViewModel.changeCity(MY_CITY)
    }

    fun chaneCategory() {
        //هر سری که دسته بندی عوض شد دوباره آگهی ها را بگیرد
        bannerViewModel.chaneCategory(MY_CATEGORY)
    }

    fun searchView() {
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
            titleList = ArrayList(listData.keys)
            adapter = ExpandableListCategoryAdapter(
                requireContext(),
                titleList as ArrayList<String>,
                listData
            )
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                val groupP = groupPosition + 1
                val childP = childPosition + 1
                MY_CATEGORY = "$groupP,$childP"

                //آپدیت لیست با تغیر دسته بندی
                chaneCategory()

                drawerLayout.closeDrawer(navView)

                false
            }
        }
    }

    override fun onItemClick(item: AdModel) {
        startActivity(Intent(requireContext(), DetailAdActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, item)
        })
    }
}