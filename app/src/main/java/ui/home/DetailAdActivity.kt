package ui.home

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.databinding.ActivityDetailAdBinding
import com.synnapps.carouselview.ImageListener
import commom.MainViewModelFactory2
import data.db.RoomDatabase.FavoriteEntity
import data.db.RoomDatabase.FavoriteRepository
import data.db.RoomDatabase.FavoriteRoomDB
import data.model.DetailModel
import data.model.PhoneModel
import kotlinx.android.synthetic.main.more_information_ad.*
import kotlinx.android.synthetic.main.more_information_ad.view.*
import ui.favorite.FavoriteViewModel
import ui.message.SendMessageActivity


class DetailAdActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityDetailAdBinding

    var img1: String? = null
    var img2: String? = null
    var img3: String? = null

    val sampleImages: ArrayList<String> = ArrayList()
    private var myFavSaved: SharedPreferences? = null
    private var editorFav: SharedPreferences.Editor? = null
    private lateinit var addFavorite: FavoriteEntity
    lateinit var viewModelFav: FavoriteViewModel
    private var favorite = false
    private lateinit var viewModelBanner: BannerViewModel
    private var tellSaved: SharedPreferences? = null
    var editorTell: SharedPreferences.Editor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail_ad)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_ad)

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")
        val city = intent.getStringExtra("city")
        var userId = intent.getIntExtra("userID", 0)
        val category = intent.getStringExtra("category")
        val date = intent.getStringExtra("date")
        val status = intent.getStringExtra("status")
        //  Toast.makeText(this, userId.toString(), Toast.LENGTH_LONG).show()


        img1 = intent.getStringExtra("img1")
        img2 = intent.getStringExtra("img2")
        img3 = intent.getStringExtra("img3")

        /*===============================image slider with CarouselView=====================*/

        if (!img1.equals(""))
            sampleImages.add(img1!!)

        if (!img2.equals(""))
            sampleImages.add(img2!!)

        if (!img3.equals(""))
            sampleImages.add(img3!!)

        mainBinding.carouselView.stopCarousel()
        mainBinding.carouselView.setImageListener(imageListener)
        mainBinding.carouselView.pageCount = sampleImages.size

        mainBinding.detailAd = DetailModel(title, price, description, date)

        /*===================================favorite=========================================*/
/*================= add and remove favorite use room database and SharedPreferences================*/
        /*  viewModelFav = ViewModelProvider(
              this,
              ViewModelProvider.AndroidViewModelFactory(application)
          ).get(FavoriteViewModel::class.java)*/
        viewModelFav = ViewModelProvider(
            this, MainViewModelFactory2(
                FavoriteRepository(
                    FavoriteRoomDB.getAppDatabase(
                        Application()
                    )!!.favoriteDao()
                )
            )
        ).get(FavoriteViewModel::class.java)

        myFavSaved = getSharedPreferences("myFav", Context.MODE_PRIVATE)
        val toggleMode = myFavSaved?.getBoolean(title, false)

        if (toggleMode!!)
            fab_fav.setImageResource(R.drawable.ic_bookmark_on)
        else
            fab_fav.setImageResource(R.drawable.ic_bookmark_off)


        mainBinding.moreLayout.fab_fav.setOnClickListener {

            if (favorite) {
                editorFav = myFavSaved?.edit()
                editorFav?.putBoolean(title, true)
                editorFav?.apply()

                fab_fav.setImageResource(R.drawable.ic_bookmark_on)
                addFavorite = FavoriteEntity(
                    id,
                    userId,
                    favorite,
                    title,
                    description,
                    price,
                    city,
                    category,
                    date,
                    img1.toString(),
                    img2.toString(),
                    img3.toString()
                )
                viewModelFav.insertInformation(addFavorite)

                Toast.makeText(this, "به لیست علاقه مندی اضافه شد", Toast.LENGTH_SHORT).show()
                favorite = false

            } else {
                editorFav = myFavSaved?.edit()
                editorFav?.putBoolean(title, false)
                editorFav?.apply()

                fab_fav.setImageResource(R.drawable.ic_bookmark_off)

                val deleteFavorite: FavoriteEntity = FavoriteEntity(id, favorite)

                viewModelFav.deleteInformation(deleteFavorite)
                Toast.makeText(this, "از لیست علاقه مندی حذف شد", Toast.LENGTH_SHORT).show()

                favorite = true
            }
        }
        /*=================================chat message======================================*/
        mainBinding.moreLayout.fab_chat.setOnClickListener {
            val goSendMessage = Intent(this, SendMessageActivity::class.java)
            startActivity(goSendMessage)
        }

/*        viewModelBanner = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(BannerViewModel::class.java)*/

        /*    viewModelBanner =
                ViewModelProvider(this, MainViewModelFactory()).get(BannerViewModel::class.java)
    */
        viewModelBanner.getMutableLiveDataTell(userId).observe(this, object : Observer<PhoneModel> {
            override fun onChanged(t: PhoneModel?) {

                tellSaved = getSharedPreferences("PHONE_NAME", Context.MODE_PRIVATE)
                editorTell = tellSaved?.edit()
                editorTell?.putString("to", t!!.tell)
                editorTell?.apply()
            }
        })

    }

    private var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {

            imageView.scaleType = ImageView.ScaleType.FIT_XY

            Glide.with(imageView.context)
                .load(sampleImages[position])
                .into(imageView)

        }
    }
}