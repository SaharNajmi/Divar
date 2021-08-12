package ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.divar.R
import com.synnapps.carouselview.ImageListener
import data.db.RoomDatabase.FavoriteEntity
import kotlinx.android.synthetic.main.activity_detail_ad.*
import kotlinx.android.synthetic.main.more_information_ad.*
import kotlinx.android.synthetic.main.more_information_ad.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ui.favorite.FavoriteViewModel
import ui.message.SendMessageActivity


class DetailAdActivity : AppCompatActivity() {
    var id = 0
    var userId = 0
    lateinit var title: String
    lateinit var price: String
    lateinit var description: String
    lateinit var city: String
    lateinit var category: String
    lateinit var date: String
    var img1: String? = null
    var img2: String? = null
    var img3: String? = null

    val bannerDetailViewModel: BannerDetailViewModel by viewModel { (parametersOf(intent.extras)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ad)

        //get value banner and show it
        detailBanner()

        backBtn.setOnClickListener {
            finish()
        }

        //chat message
        fab_chat.setOnClickListener {
            val goSendMessage = Intent(this, SendMessageActivity::class.java)
            startActivity(goSendMessage)
        }

    }


    fun detailBanner() {
        bannerDetailViewModel.bannerLiveData.observe(this) {
            id = it.id
            title = it.title
            price = it.price
            description = it.description
            city = it.city
            userId = it.userID
            category = it.category
            date = it.date
            img1 = it.img1
            img2 = it.img2
            img3 = it.img3

            title_detail.text = title
            price_detail.text = price
            date_detail.text = date
            description_detail.text = description
            location_detail.text = city
        }
    }
}