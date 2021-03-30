package view

import adapter.AdAdapter
import adapter.ItemOnClickListener
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_ad_list.*
import kotlinx.android.synthetic.main.toolbar.*
import model.AdModel
import viewmodel.BannerViewModel

class AdListFragment : Fragment(), ItemOnClickListener {

    private val cityArray = listOf("کردستان", "تهران", "اردبیل")
    private var editor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*====================================show Ad list===========================================*/
        //آگهی ها را بر اساس شهر انتخاب شده نمایش دهد
        var myDataSaved = activity?.getSharedPreferences("myCity", Context.MODE_PRIVATE)
        val cityName = myDataSaved?.getString("CityName", "all")

        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

        val listMutableLiveData: MutableLiveData<ArrayList<AdModel>> =
            viewModel.getListMutableLiveData(cityName!!, "all")

        listMutableLiveData.observe(requireActivity(), object : Observer<ArrayList<AdModel>> {
            override fun onChanged(t: ArrayList<AdModel>?) {

                val adapter = AdAdapter(requireContext(), t!!, this@AdListFragment)
                val manager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                rec_ad.layoutManager = manager
                rec_ad.adapter = adapter
            }
        })

        /*==================================spinner city======================================*/
        val adapterCity = ArrayAdapter<String>(
            requireContext(),
            R.layout.dropdown_menu, cityArray
        )
        spinner_city.adapter = adapterCity

        myDataSaved = requireActivity().getSharedPreferences("myCity", Context.MODE_PRIVATE)
        val cityCode = myDataSaved?.getInt("spinnerSelectionCity", 0)
        spinner_city.setSelection(cityCode!!)

        spinner_city.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedPosition: Int = spinner_city.selectedItemPosition
                val cityName = cityArray[cityCode]

                editor = myDataSaved?.edit()
                editor?.putInt("spinnerSelectionCity", selectedPosition)
                editor?.putString("CityName", cityName)
                editor?.apply()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
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