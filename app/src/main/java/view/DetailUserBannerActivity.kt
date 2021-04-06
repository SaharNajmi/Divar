package view

import android.app.AlertDialog
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import api.UriToUploadable
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.databinding.ActivityDetailUserBannerBinding
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.delete_and_edit_layout.view.*
import kotlinx.android.synthetic.main.dialog_edit.view.*
import model.DetailModel
import model.ListCity
import model.MSG
import okhttp3.MultipartBody
import okhttp3.RequestBody
import viewmodel.BannerViewModel
import java.util.*
import kotlin.collections.ArrayList

class DetailUserBannerActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityDetailUserBannerBinding

    var id: Int? = null
    var userId: Int? = null
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

    private var post_img_1: MultipartBody.Part? = null
    private var post_img_2: MultipartBody.Part? = null
    private var post_img_3: MultipartBody.Part? = null

    private val REQUEST_CODE_IMG_1 = 1
    private val REQUEST_CODE_IMG_2 = 2
    private val REQUEST_CODE_IMG_3 = 3

    private var imageUri: Uri? = null

    private var validate1 = false
    private var validate2 = false
    private var validate3 = false
    private var validate4 = false

    val sampleImages: ArrayList<String> = ArrayList()
    private lateinit var viewModel: BannerViewModel
    private var menuItems: ListCity? = null
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
        userId = intent.getIntExtra("userID", 0)
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

        /*=================================delete banner==============================================*/
        mainBinding.layoutDelEdit.fab_del.setOnClickListener {
            showDialogDelete()
        }

        /*=================================edit banner==============================================*/
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
        customLayout = layoutInflater.inflate(R.layout.dialog_edit, null)

        //create alert dialog
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("ویرایش آگهی")
            .setView(customLayout)
            .setPositiveButton("ثبت", null)
            .setNegativeButton("انصراف", null)
            .show()

        //edit image
        editImage()


        // prevent a dialog from closing when a button is clicked
        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

        //show old value in dialog box
        customLayout.txt_edit_title.setText(title)
        customLayout.txt_edit_description.setText(description)
        customLayout.txt_edit_price.setText(price)
        customLayout.exposed_dropdown_edit.setText(city)
        showSpinnerCateInDialogBox()

        //spinner list city
        searchListCity()
        positiveButton.setOnClickListener {

            if (customLayout.txt_edit_title.text.toString().trim().length >= 10) {
                customLayout.title_layout.isErrorEnabled = false
                validate2 = true

            } else {
                customLayout.title_layout.error = "عنوان آگهی باید حداقل 10 حرف باشد"
                customLayout.title_layout.isErrorEnabled = true
                validate2 = false
            }

            if (customLayout.txt_edit_description.text.toString().trim().length >= 30) {
                customLayout.description_layout.isErrorEnabled = false
                validate3 = true

            } else {
                customLayout.description_layout.error = "توضیحات آگهی باید حداقل 30 حرف باشد";
                customLayout.description_layout.isErrorEnabled = true
                validate3 = false
            }

            if (customLayout.txt_edit_price.text.toString().trim()
                    .isEmpty() || Integer.parseInt(customLayout.txt_edit_price.text.toString()) <= 0
            ) {
                customLayout.price_layout.error = "قیمت وارد شده اشتباه است"
                customLayout.price_layout.isErrorEnabled = true
                validate4 = false
            } else {
                customLayout.price_layout.isErrorEnabled = false
                validate4 = true
            }

            if (validate1 && validate2 && validate3 && validate4) {

                //RequestBody  به خاطر اینکه مقادیری که به سمت سرور ارسال میشوند داخل""قرار نگیرند
                val newTitle =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.txt_edit_title.text.toString()
                    )
                val newDesc =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.txt_edit_description.text.toString()
                    )
                val newPrice =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.txt_edit_price.text.toString()
                    )
                val newCity =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.exposed_dropdown_edit.text.toString()
                    )

                selectedPositionCateSub = customLayout.spinner_edit_cate_sub.selectedItemPosition!!
                selectedPositionCateBase = selectedPositionCateBase?.plus(1)
                selectedPositionCateSub = selectedPositionCateSub?.plus(1)
                val newCate = RequestBody.create(
                    okhttp3.MultipartBody.FORM,
                    "$selectedPositionCateBase,$selectedPositionCateSub"
                )


                val msgEditBanner = viewModel.editBanner(
                    id!!,
                    newTitle,
                    newDesc,
                    newPrice,
                    userId!!,
                    newCity,
                    newCate,
                    post_img_1!!,
                    post_img_2!!,
                    post_img_3!!
                )
                msgEditBanner.observe(this, object : Observer<MSG> {
                    override fun onChanged(t: MSG?) {
                        Toast.makeText(this@DetailUserBannerActivity, t!!.msg, Toast.LENGTH_LONG)
                            .show()

                        mainBinding.detailAd = DetailModel(title!!, price!!, description!!, date!!)
                        dialog.dismiss()
                    }
                })
            } else {

                Toast.makeText(this, "تمام فیلد ها را پر کنید!!!", Toast.LENGTH_SHORT)
                    .show()

            }

        }

    }

    private fun showDialogDelete() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("آیا میخواهید این آگهی را حذف کنید؟")
        builder.setPositiveButton(
            "بله",
            DialogInterface.OnClickListener { dialog, which ->
                val delete = viewModel.deleteBanner(id!!)
                delete.observe(this, object : Observer<MSG> {
                    override fun onChanged(t: MSG?) {
                        Toast.makeText(
                            this@DetailUserBannerActivity,
                            t!!.msg,
                            Toast.LENGTH_LONG
                        )
                            .show()
                        finish()
                    }
                })
            })
        builder.setNegativeButton(
            "خیر",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })

        builder.create().show()
    }

    /*===========================spinner category===============================**/
    private fun showSpinnerCateInDialogBox() {
        //split string category
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

        //Show subcategories when change baseCategory
        customLayout.spinner_edit_cate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

    /*================================spinner list city==================================*/
    private fun searchListCity() {
        menuItems = ListCity()
        val list = menuItems!!.arrayListCity()
        val adapter = ArrayAdapter(this, R.layout.dropdown_menu, list)
        customLayout.exposed_dropdown_edit.setAdapter(adapter)
        customLayout.exposed_dropdown_edit.setOnFocusChangeListener(object :
            View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (!hasFocus) {
                    if (!list.contains(customLayout.exposed_dropdown_edit.text.toString())) {
                        customLayout.exposed_dropdown_edit.setText("")
                        customLayout.city_layout.error = "شهر خود را انتخاب کنید"
                        customLayout.city_layout.isErrorEnabled = true
                        validate1 = false
                    } else {
                        customLayout.city_layout.isErrorEnabled = false
                        validate1 = true
                    }
                }
            }
        })
    }

    private fun editImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        customLayout.image_1.setOnClickListener {
            startActivityForResult(gallery, REQUEST_CODE_IMG_1)
        }
        customLayout.image_2.setOnClickListener {
            startActivityForResult(gallery, REQUEST_CODE_IMG_2)
        }
        customLayout.image_3.setOnClickListener {
            startActivityForResult(gallery, REQUEST_CODE_IMG_3)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val upload = UriToUploadable(this)
        /*   فایل با چه اسمی آپلود شود
          UUID.randomUUID(): فایل با اسم تصادفی آپلود شود*/

        if (resultCode == RESULT_OK) {

            imageUri = data?.data

            if (requestCode == REQUEST_CODE_IMG_1) {
                post_img_1 = upload.getUploaderFile(imageUri, "image1", "${UUID.randomUUID()}")
                customLayout.image_1.setImageURI(imageUri)
            }

            if (requestCode == REQUEST_CODE_IMG_2) {
                post_img_2 = upload.getUploaderFile(imageUri, "image2", "${UUID.randomUUID()}")
                customLayout.image_2.setImageURI(imageUri)
            }

            if (requestCode == REQUEST_CODE_IMG_3) {
                post_img_3 = upload.getUploaderFile(imageUri, "image3", "${UUID.randomUUID()}")
                customLayout.image_3.setImageURI(imageUri)
            }
        }
    }
}