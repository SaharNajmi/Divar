package ui.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.divar.R
import com.synnapps.carouselview.ImageListener
import commom.BASE_URL
import data.model.ListCity
import data.model.MSG
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_ad.*
import kotlinx.android.synthetic.main.delete_and_edit_layout.*
import kotlinx.android.synthetic.main.dialog_edit.view.*
import kotlinx.android.synthetic.main.more_information_ad.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import service.UriToUploadable
import timber.log.Timber
import ui.auth.UserViewModel
import ui.favorite.FavoriteViewModel
import ui.message.SendMessageActivity
import java.util.*

class DetailAdActivity : AppCompatActivity() {
    var id = 0
    var userId = 0
    lateinit var title: String
    lateinit var price: String
    lateinit var description: String
    lateinit var city: String
    lateinit var category: String
    lateinit var date: String
    lateinit var phone: String
    var img1: String? = null
    var img2: String? = null
    var img3: String? = null
    var favorite: Boolean = false
    lateinit var customLayout: View
    private val REQUEST_CODE_IMG_1 = 1
    private val REQUEST_CODE_IMG_2 = 2
    private val REQUEST_CODE_IMG_3 = 3
    private var imageUri: Uri? = null
    private var postImage1: MultipartBody.Part? = null
    private var postImage2: MultipartBody.Part? = null
    private var postImage3: MultipartBody.Part? = null
    private var validate1 = false
    private var validate2 = false
    private var validate3 = false
    private var validate4 = false
    lateinit var newTitle: RequestBody
    lateinit var newDesc: RequestBody
    lateinit var newPrice: RequestBody
    lateinit var newCity: RequestBody
    lateinit var newCate: RequestBody
    private var menuItems: ListCity? = null
    private var selectedPositionCateBase: Int? = null
    private var selectedPositionCateSub: Int? = null
    var sampleImages: ArrayList<String> = ArrayList()

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
    val compositeDisposable = CompositeDisposable()
    val bannerDetailViewModel: BannerDetailViewModel by viewModel { (parametersOf(intent.extras)) }
    val favoriteViewModel: FavoriteViewModel by viewModel()
    val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ad)

        //ui blocked (error->AndroidBlockGuardPolicy)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //get value banner and show it
        detailBanner()

        backBtn.setOnClickListener {
            finish()
        }

        //call phone number
        fab_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            startActivity(intent)
        }

        //go activity send message
        img_chat.setOnClickListener {
            //اگر کاربر لاگین کرده بود بتونه پیام بفرسته
            if (userViewModel.isSignIn) {
                val goSendMessage = Intent(this, SendMessageActivity::class.java)
                goSendMessage.putExtra("RECEIVER", phone)
                goSendMessage.putExtra("SENDER", userViewModel.phoneNumber)
                goSendMessage.putExtra("BANNER_IMAGE", img1)
                goSendMessage.putExtra("BANNER_ID", id)
                goSendMessage.putExtra("BANNER_TITLE", title)
                startActivity(goSendMessage)
            } else
                Toast.makeText(this, "ابتدا وارد حساب خود شوید!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun imageSlider() {
        if (!img1.equals(""))
            sampleImages.add(img1!!)

        if (!img2.equals(""))
            sampleImages.add(img2!!)

        if (!img3.equals(""))
            sampleImages.add(img3!!)

        carouselView.pageCount = sampleImages!!.size
        carouselView.stopCarousel()
        carouselView.setImageListener(imageListener)

    }

    private var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {

            imageView.scaleType = ImageView.ScaleType.FIT_XY

            Glide.with(imageView.context)
                .load("${BASE_URL}${sampleImages[position]}")
                .into(imageView)

        }
    }

    private fun showEditOrDeleteLayout() {
        /* اگه شماره موبایل این آگهی با شماره موبایل کاربری که لاگین کرده یکی  باشه یعنی
         این آگهی مال اونه پس باید بتونه ویرایش و حذف را انجام دهد*/
        if (userViewModel.phoneNumber == phone) {
            layout_del_edit.visibility = View.VISIBLE
            more_layout.visibility = View.GONE
            img_delete.setOnClickListener { showDialogDelete() }
            img_edit.setOnClickListener { showDialogEdit() }
        } else {
            layout_del_edit.visibility = View.GONE
            more_layout.visibility = View.VISIBLE
        }
    }

    private fun deleteBanner() {
        userViewModel.deleteBanner(id)
            .observeOn(Schedulers.newThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MSG> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: MSG) {
                    //  Toast.makeText(this@DetailAdActivity, t.msg.toString(), Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

            })

    }

    private fun editBanner() {
        userViewModel.editBanner(
            id,
            newTitle,
            newDesc,
            newPrice,
            userId,
            newCity,
            newCate,
            postImage1!!,
            postImage2!!,
            postImage3!!
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MSG> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: MSG) {
                    Toast.makeText(this@DetailAdActivity, t.msg, Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
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
            phone = it.tell
            date = it.date
            img1 = it.img1
            img2 = it.img2
            img3 = it.img3
            title_detail.text = title
            price_detail.text = price
            date_detail.text = date
            description_detail.text = description
            location_detail.text = city

            //image slider
            imageSlider()

            //show layout edit or layout
            showEditOrDeleteLayout()

            //favorite
            favorite = it.favorite
            checkFavorite(favorite)

            val favbanner = it
            img_fav.setOnClickListener {
                if (favorite) {
                    img_fav.setImageResource(R.drawable.ic_dont_favorite)
                    favoriteViewModel.deleteFavorite(favbanner)
                    favorite = false

                } else {
                    img_fav.setImageResource(R.drawable.ic_favorite)
                    favoriteViewModel.addFavorite(favbanner)
                    favorite = true
                }
            }

        }
    }

    fun checkFavorite(favorite: Boolean) {
        if (favorite)
            img_fav.setImageResource(R.drawable.ic_favorite)
        else
            img_fav.setImageResource(R.drawable.ic_dont_favorite)
    }

    //alert dialog edit banner
    private fun showDialogEdit() {
        customLayout = layoutInflater.inflate(R.layout.dialog_edit, null)

        //create alert dialog
        val dialog = AlertDialog.Builder(this)
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

        Glide.with(this)
            .load(img1)
            .into(customLayout.image_1)

        Glide.with(this)
            .load(img2)
            .into(customLayout.image_2)

        Glide.with(this)
            .load(img3)
            .into(customLayout.image_3)

        showSpinnerCateInDialogBox()

        //spinner list city
        searchListCity()
        positiveButton.setOnClickListener {

            if (customLayout.txt_edit_title.text.toString().trim().length >= 10) {
                customLayout.title_layout.isErrorEnabled = false
                validate2 = true

            } else {
                customLayout.title_layout.error = "عنوان آگهی باید حداقل 10 کلمه باشد"
                customLayout.title_layout.isErrorEnabled = true
                validate2 = false
            }

            if (customLayout.txt_edit_description.text.toString().trim().length >= 30) {
                customLayout.description_layout.isErrorEnabled = false
                validate3 = true

            } else {
                customLayout.description_layout.error = "توضیحات آگهی باید حداقل 30 کلمه باشد";
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
                newTitle =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.txt_edit_title.text.toString()
                    )
                newDesc =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.txt_edit_description.text.toString()
                    )
                newPrice =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.txt_edit_price.text.toString()
                    )
                newCity =
                    RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        customLayout.exposed_dropdown_edit.text.toString()
                    )

                selectedPositionCateSub = customLayout.spinner_edit_cate_sub.selectedItemPosition!!
                selectedPositionCateBase = selectedPositionCateBase?.plus(1)
                selectedPositionCateSub = selectedPositionCateSub?.plus(1)
                newCate = RequestBody.create(
                    okhttp3.MultipartBody.FORM,
                    "$selectedPositionCateBase,$selectedPositionCateSub"
                )

                //edit user banner
                editBanner()
            } else {

                Toast.makeText(this, "تمام فیلد ها را پر کنید!!!", Toast.LENGTH_SHORT)
                    .show()

            }

        }

    }

    private fun showDialogDelete() {
        //create alert dialog
        AlertDialog.Builder(this)
            .setTitle("حذف آگهی")
            .setMessage("آیا میخواهید این آگهی را حذف کنید؟")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setNegativeButton("خیر", null)
            .setPositiveButton("بله") { dialogInterface, which ->

                //delete banner
                deleteBanner()
            }
            .show()
    }

    //spinner category
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
                        this@DetailAdActivity,
                        android.R.layout.simple_spinner_item, cate_sub
                    )
                    adapterCateSub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    customLayout.spinner_edit_cate_sub.adapter = adapterCateSub

                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {
                }
            }
    }

    //spinner list city
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
                postImage1 = upload.getUploaderFile(imageUri, "image1", "${UUID.randomUUID()}")
                customLayout.image_1.setImageURI(imageUri)

                Glide.with(this)
                    .load(data!!.data)
                    .into(customLayout.image_1)
            }

            if (requestCode == REQUEST_CODE_IMG_2) {
                postImage2 = upload.getUploaderFile(imageUri, "image2", "${UUID.randomUUID()}")
                customLayout.image_2.setImageURI(imageUri)

                Glide.with(this)
                    .load(data!!.data)
                    .into(customLayout.image_2)
            }

            if (requestCode == REQUEST_CODE_IMG_3) {
                postImage3 = upload.getUploaderFile(imageUri, "image3", "${UUID.randomUUID()}")
                customLayout.image_3.setImageURI(imageUri)

                Glide.with(this)
                    .load(data!!.data)
                    .into(customLayout.image_3)
            }
        }
    }
}