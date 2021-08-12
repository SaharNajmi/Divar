package ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.divar.R
import commom.ItemOnClickListener
import data.model.AdModel
import kotlinx.android.synthetic.main.activity_category.*

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

/*            val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)*/
        //  val viewModel = ViewModelProvider(this, MainViewModelFactory()).get(BannerViewModel::class.java)viewModel.applyActivationKey(txtTell, txtCode)

        val viewModel = ViewModelProvider(this).get(BannerViewModel::class.java)

        /*     viewModel.getListLiveData(cityName!!, category!!)
                 .observe(this, object : Observer<ArrayList<AdModel>> {
                     override fun onChanged(t: ArrayList<AdModel>?) {

                         val adapter = AdAdapter(this@CategoryActivity, t!!, this@CategoryActivity)
                         val manager =
                             GridLayoutManager(this@CategoryActivity, 2, RecyclerView.VERTICAL, false)
                         rec_cate.layoutManager = manager
                         adapter.updateList(t)
                         rec_cate.adapter = adapter
                     }
                 })*/

    }

    override fun onItemClick(item: AdModel) {
        val go = Intent(this, DetailAdActivity::class.java)
        go.putExtra("id", item.id)
        go.putExtra("title", item.title)
        go.putExtra("description", item.description)
        go.putExtra("price", item.price)
        go.putExtra("city", item.city)
        go.putExtra("tell", item.userID)
        go.putExtra("category", item.category)
        go.putExtra("date", item.date)
        go.putExtra("status", item.status)
        go.putExtra("img1", item.img1)
        go.putExtra("img2", item.img2)
        go.putExtra("img3", item.img3)
        startActivity(go)
    }
}