package view

import adapter.AdAdapter
import adapter.ItemOnClickListener
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.MainActivity
import com.example.divar.R
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_ad_list.*
import model.AdModel
import viewmodel.BannerViewModel

class CategoryActivity : AppCompatActivity(), ItemOnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val title = intent.getStringExtra("titleCate")
        txt_cate.text = title

        /*================نشان دادن آگهی ها بر اساس شهر و دسته بندی======================*/

        val category = intent.getStringExtra("category")

        val myDataSaved = getSharedPreferences("myCity", Context.MODE_PRIVATE)
        val cityName = myDataSaved?.getString("CityName", "all")

        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

        val listMutableLiveData: MutableLiveData<ArrayList<AdModel>> =
            viewModel.getListMutableLiveData(cityName!!, category!!)

        listMutableLiveData.observe(this, object : Observer<ArrayList<AdModel>> {
            override fun onChanged(t: ArrayList<AdModel>?) {

                val adapter = AdAdapter(this@CategoryActivity, t!!, this@CategoryActivity)
                val manager =
                    GridLayoutManager(this@CategoryActivity, 2, RecyclerView.VERTICAL, false)
                rec_cate.layoutManager = manager
                adapter.updateList(t)
                rec_cate.adapter = adapter
            }
        })

    }

    override fun onItemClick(item: AdModel) {
        val go = Intent(this, DetailAdActivity::class.java)
        go.putExtra("id", item.id)
        go.putExtra("title", item.title)
        go.putExtra("description", item.description)
        go.putExtra("price", item.price)
        go.putExtra("city", item.city)
        go.putExtra("tell", item.tell)
        go.putExtra("category", item.category)
        go.putExtra("date", item.date)
        go.putExtra("status", item.status)
        go.putExtra("img1", item.img1)
        go.putExtra("img2", item.img2)
        go.putExtra("img3", item.img3)
        startActivity(go)
    }
}