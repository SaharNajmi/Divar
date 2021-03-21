package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.databinding.ActivityDetailAdBinding
import com.example.divar.databinding.ActivityDetailUserBannerBinding
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.delete_and_edit_layout.view.*
import model.DetailModel

class DetailUserBannerActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityDetailUserBannerBinding

    var img1: String? = null
    var img2: String? = null
    var img3: String? = null

    val sampleImages: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_detail_user_banner)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_user_banner)

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")
        val city = intent.getStringExtra("city")
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

        mainBinding.layoutDelEdit.fab_del.setOnClickListener { }
        mainBinding.layoutDelEdit.fab_edit.setOnClickListener { }
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