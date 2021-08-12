package ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import commom.ItemOnClickListener
import commom.LIST_CITY
import commom.MY_CATEGORY
import commom.MY_CITY
import data.model.AdModel
import kotlinx.android.synthetic.main.fragment_ad_list.*
import kotlinx.android.synthetic.main.fragment_new_ad.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class AdListFragment : Fragment(), ItemOnClickListener {

    lateinit var bannerAdapter: BannerAdapter

    //instance viewModel use koin
    val bannerViewModel: BannerViewModel by viewModel<BannerViewModel>() {
        parametersOf(
            MY_CITY,
            MY_CATEGORY
        )
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

    override fun onItemClick(item: AdModel) {
        val go = Intent(context, DetailAdActivity::class.java)
        go.putExtra("id", item.id)
        go.putExtra("title", item.title)
        go.putExtra("description", item.description)
        go.putExtra("price", item.price)
        go.putExtra("city", item.city)
        go.putExtra("userID", item.userID)
        go.putExtra("category", item.category)
        go.putExtra("date", item.date)
        go.putExtra("status", item.status)
        go.putExtra("img1", item.img1)
        go.putExtra("img2", item.img2)
        go.putExtra("img3", item.img3)
        startActivity(go)
    }
}