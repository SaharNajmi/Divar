package view

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.divar.R
import com.example.divar.databinding.ActivityDetailAdBinding
import kotlinx.android.synthetic.main.ad_list_item.*
import model.AdModel
import model.DetailModel
import viewmodel.BannerViewModel

class DetailAdActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityDetailAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail_ad)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_ad)

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")
        val city = intent.getStringExtra("city")
        val tell = intent.getStringExtra("tell")
        val category = intent.getStringExtra("category")
        val date = intent.getStringExtra("date")
        val status = intent.getStringExtra("status")
        val img1 = intent.getStringExtra("img1")
        val img2 = intent.getStringExtra("img2")
        val img3 = intent.getStringExtra("img3")

        mainBinding.detailAd = DetailModel(title, price, description, date, img1)

    }
}