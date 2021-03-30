package view

import RoomDatabase.FavoriteEntity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.databinding.ActivityDetailAdBinding
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.more_information_ad.*
import kotlinx.android.synthetic.main.more_information_ad.view.*
import model.DetailModel
import viewmodel.FavoriteViewModel


class DetailAdActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityDetailAdBinding

    var img1: String? = null
    var img2: String? = null
    var img3: String? = null

    val sampleImages: ArrayList<String> = ArrayList()
    private var myFavSaved: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private lateinit var addFavorite: FavoriteEntity
    lateinit var viewModel: FavoriteViewModel
    private var favorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail_ad)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_ad)

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")
        val city = intent.getStringExtra("city")
        val userId = intent.getIntExtra("userID",0)
        val category = intent.getStringExtra("category")
        val date = intent.getStringExtra("date")
        val status = intent.getStringExtra("status")

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
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(FavoriteViewModel::class.java)

        myFavSaved = getSharedPreferences("myFav", Context.MODE_PRIVATE)
        val toggleMode = myFavSaved?.getBoolean(title, false)

        if (toggleMode!!)
            fab_fav.setImageResource(R.drawable.ic_bookmark_on)
        else
            fab_fav.setImageResource(R.drawable.ic_bookmark_off)


        mainBinding.moreLayout.fab_fav.setOnClickListener {

            if (favorite) {
                editor = myFavSaved?.edit()
                editor?.putBoolean(title, true)
                editor?.apply()

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
                viewModel.insertInformation(addFavorite, this)

                Toast.makeText(this, "به لیست علاقه مندی اضافه شد", Toast.LENGTH_SHORT).show()
                favorite = false

            } else {
                editor = myFavSaved?.edit()
                editor?.putBoolean(title, false)
                editor?.apply()

                fab_fav.setImageResource(R.drawable.ic_bookmark_off)

                val deleteFavorite: FavoriteEntity = FavoriteEntity(id, favorite)

                viewModel.deleteInformation(deleteFavorite, this)
                Toast.makeText(this, "از لیست علاقه مندی حذف شد", Toast.LENGTH_SHORT).show()

                favorite = true
            }
        }

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