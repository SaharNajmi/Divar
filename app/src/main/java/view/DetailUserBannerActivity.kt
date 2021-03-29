package view

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.databinding.ActivityDetailUserBannerBinding
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.delete_and_edit_layout.view.*
import kotlinx.android.synthetic.main.dialog_edit.*
import kotlinx.android.synthetic.main.dialog_edit.view.*
import kotlinx.android.synthetic.main.fragment_new_ad.*
import model.DetailModel
import model.MSG
import viewmodel.BannerViewModel

class DetailUserBannerActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityDetailUserBannerBinding

    var id: Int? = null
    var title: String? = null
    var price: String? = null
    var description: String? = null
    var city: String? = null
    var category: String? = null
    var date: String? = null
    var status: String? = null
    var img1: String? = null
    var img2: String? = null
    var img3: String? = null

    val sampleImages: ArrayList<String> = ArrayList()
    private lateinit var viewModel: BannerViewModel
    lateinit var customLayout: View
    private var selectedPositionCateBase: Int? = null
    private var selectedPositionCateSub: Int? = null

    private val cate_base = arrayOf(
        "لوازم الکترونیکی", "املاک", "وسایل نقلیه"
    )
    val cate_0 = arrayOf(
        "موبایل", "تبلت", "لپ تاپ"
    )
    val cate_1 = arrayOf(
        "رهن و اجاره", "خرید و فروش"
    )
    val cate_2 = arrayOf(
        "خودرو", "موتور سیکلت", "اجاره خودرو", "کشاورزی"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_detail_user_banner)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_user_banner)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

        id = intent.getIntExtra("id", 0)
        title = intent.getStringExtra("title")
        price = intent.getStringExtra("price")
        description = intent.getStringExtra("description")
        city = intent.getStringExtra("city")
        category = intent.getStringExtra("category")
        date = intent.getStringExtra("date")
        status = intent.getStringExtra("status")

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

        mainBinding.detailAd = DetailModel(title!!, price!!, description!!, date!!)
        mainBinding.layoutDelEdit.fab_del.setOnClickListener {
            val delete = viewModel.deleteBanner(id!!)
            delete.observe(this, object : Observer<MSG> {
                override fun onChanged(t: MSG?) {
                    Toast.makeText(this@DetailUserBannerActivity, t!!.msg, Toast.LENGTH_LONG).show()

                    finish()
                }
            })
        }
        mainBinding.layoutDelEdit.fab_edit.setOnClickListener {
            showDialogEdit()
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

    /*==============================alert dialog edit banner==============================================*/
    private fun showDialogEdit() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ویرایش آگهی")
        customLayout = getLayoutInflater().inflate(R.layout.dialog_edit, null);
        builder.setView(customLayout)

        //show old value in dialog box
        customLayout.txt_edit_title.setText(title)
        customLayout.txt_edit_description.setText(description)
        customLayout.txt_edit_price.setText(price)
        customLayout.exposed_dropdown.setText(city)
        showSpinnerCateInDialogBox()

        builder.setPositiveButton("ثبت", DialogInterface.OnClickListener { dialog, which ->

        })
        builder.setNegativeButton(
            "انصراف",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()

    }

    /*===========================spinner category===============================*/
    private fun showSpinnerCateInDialogBox() {
        val splitCate = category!!.split(",").toTypedArray()
        var cateBase = splitCate[0].toInt()
        cateBase -= 1
        var cateSub = splitCate[1].toInt()
        cateSub -= 1

        //show cate base
        val adapterCate = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, cate_base
        )
        //view dropdown
        adapterCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        customLayout.spinner_edit_cate.adapter = adapterCate

        //item default spinner cate base
        customLayout.spinner_edit_cate.setSelection(cateBase)

        //show cate sub
        var cate_sub = cate_0
        cate_sub = when (cateBase) {
            0 -> cate_0
            1 -> cate_1
            else -> cate_2
        }
        var adapterCateSub = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, cate_sub
        )
        adapterCateSub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        customLayout.spinner_edit_cate_sub.adapter = adapterCateSub

        //item default spinner cate sub
        customLayout.spinner_edit_cate_sub.setSelection(cateSub)
        var category = ""

        //Show subcategories when change baseCategory
        customLayout.spinner_edit_cate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                selectedPositionCateBase = customLayout.spinner_edit_cate.selectedItemPosition
                cate_sub = when (selectedPositionCateBase) {
                    0 -> cate_0
                    1 -> cate_1
                    else -> cate_2
                }
                 adapterCateSub = ArrayAdapter<String>(
                   this@DetailUserBannerActivity,
                    android.R.layout.simple_spinner_item, cate_sub
                )
                adapterCateSub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                customLayout.spinner_edit_cate_sub.adapter = adapterCateSub

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }
    }
}