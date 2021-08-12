package feature.add

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.divar.R
import commom.MainViewModelFactory
import data.model.ListCity
import data.model.MSG
import data.model.UserIdModel
import feature.home.BannerViewModel
import kotlinx.android.synthetic.main.fragment_new_ad.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import service.UriToUploadable
import java.util.*

class NewAdFragment : Fragment() {
    private var validate1 = false
    private var validate2 = false
    private var validate3 = false
    private var validate4 = false
    private var menuItems: ListCity? = null
    private var selectedPositionCateBase: Int? = null
    private var selectedPositionCateSub: Int? = null
    var userId: Int? = null
    private lateinit var pref: SharedPreferences
    private lateinit var viewModel: BannerViewModel
    private val REQUEST_CODE_IMG_1 = 1
    private val REQUEST_CODE_IMG_2 = 2
    private val REQUEST_CODE_IMG_3 = 3
    private var load_img_1 = false
    private var load_img_2 = false
    private var load_img_3 = false
    private var imageUri: Uri? = null
    private var post_img_1: MultipartBody.Part? = null
    private var post_img_2: MultipartBody.Part? = null
    private var post_img_3: MultipartBody.Part? = null

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_ad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)*/

        viewModel = ViewModelProvider(this, MainViewModelFactory()).get(BannerViewModel::class.java)

        /*==============use AutoCompleteTextView for search list city=====================*/
        menuItems = ListCity()
        val list = menuItems!!.arrayListCity()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, list)
        exposed_dropdown.setAdapter(adapter)
        exposed_dropdown.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (!hasFocus) {
                    if (!list.contains(exposed_dropdown.text.toString())) {
                        exposed_dropdown.setText("")
                        city_layout.error = "شهر خود را انتخاب کنید"
                        city_layout.isErrorEnabled = true
                        validate1 = false
                    } else {
                        city_layout.isErrorEnabled = false
                        validate1 = true
                    }
                }
            }
        })

        /*===========================spinner category===============================*/
        val adapterCate = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, cate_base
        )
        adapterCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_cate.adapter = adapterCate
        var category = RequestBody.create(okhttp3.MultipartBody.FORM, "")
        spinner_cate.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                var cateSub = cate_0
                selectedPositionCateBase = spinner_cate.selectedItemPosition
                cateSub = when (selectedPositionCateBase) {
                    0 -> cate_0
                    1 -> cate_1
                    else -> cate_2
                }
                val adapterCateSub = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, cateSub
                )
                adapterCateSub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner_cate_sub.adapter = adapterCateSub

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }
        /*==============================get userId================================*/
        pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val phone = pref.getString("tell", "")

        viewModel.getMutableLiveDataId(phone!!)
            .observe(requireActivity(), object : Observer<UserIdModel> {
                override fun onChanged(t: UserIdModel?) {
                    userId = t!!.id

                }
            })

        /*===============================button submit add banner======================================*/
        btn_add_banner.setOnClickListener {

            selectedPositionCateSub = spinner_cate_sub.selectedItemPosition!!
            selectedPositionCateBase = selectedPositionCateBase?.plus(1)
            selectedPositionCateSub = selectedPositionCateSub?.plus(1)
            category = RequestBody.create(
                okhttp3.MultipartBody.FORM,
                "$selectedPositionCateBase,$selectedPositionCateSub"
            )

            if (!validate1) {
                city_layout.error = "شهر خود را انتخاب کنید"
                city_layout.isErrorEnabled = true
                validate1 = false
            } else {
                city_layout.isErrorEnabled = false
                validate1 = true
            }


            if (txt_title.text.toString().trim().length >= 10) {
                title_layout.isErrorEnabled = false
                validate2 = true

            } else {
                title_layout.error = "عنوان آگهی باید حداقل 10 حرف باشد"
                title_layout.isErrorEnabled = true
                validate2 = false
            }

            if (txt_description.text.toString().trim().length >= 30) {
                description_layout.isErrorEnabled = false
                validate3 = true

            } else {
                description_layout.error = "توضیحات آگهی باید حداقل 30 حرف باشد";
                description_layout.isErrorEnabled = true
                validate3 = false
            }

            if (txt_price.text.toString().trim()
                    .isEmpty() || Integer.parseInt(txt_price.text.toString()) <= 0
            ) {
                price_layout.error = "قیمت وارد شده اشتباه است"
                price_layout.isErrorEnabled = true
                validate4 = false
            } else {
                price_layout.isErrorEnabled = false
                validate4 = true
            }


            if (validate1 && validate2 && validate3 && validate4) {
                //RequestBody  به خاطر اینکه مقادیری که به سمت سرور ارسال میشوند داخل""قرار نگیرند
                val title =
                    RequestBody.create(okhttp3.MultipartBody.FORM, txt_title.text.toString())
                val desc =
                    RequestBody.create(okhttp3.MultipartBody.FORM, txt_description.text.toString())
                val price =
                    RequestBody.create(okhttp3.MultipartBody.FORM, txt_price.text.toString())
                val city =
                    RequestBody.create(okhttp3.MultipartBody.FORM, exposed_dropdown.text.toString())

                /*    val upload = UriToUploadable(requireActivity())
                    *//*   فایل با چه اسمی آپلود شود
                  UUID.randomUUID(): فایل با اسم تصادفی آپلود شود*//*

                val postImage = upload.getUploaderFile(imageUri, "image1", "${UUID.randomUUID()}")
*/
                val msgAddBanner =
                    viewModel.addBanner(
                        title,
                        desc,
                        price,
                        userId!!,
                        city,
                        category,
                        post_img_1!!,
                        post_img_2!!,
                        post_img_3!!
                    )
                msgAddBanner.observe(requireActivity(), object : Observer<MSG> {
                    override fun onChanged(t: MSG?) {
                        Toast.makeText(requireContext(), t!!.msg, Toast.LENGTH_LONG).show()

                        //empty text value old
                        txt_title.text.clear()
                        txt_description.text.clear()
                        txt_price.text.clear()
                        exposed_dropdown.text.clear()
                        spinner_cate.setSelection(0)
                        spinner_cate_sub.setSelection(0)

                        delete_img_1.visibility = View.GONE
                        delete_img_2.visibility = View.GONE
                        delete_img_3.visibility = View.GONE

                        image_1.setImageResource(R.drawable.ic_image)
                        image_2.setImageResource(R.drawable.ic_image)
                        image_3.setImageResource(R.drawable.ic_image)
                    }
                })

            } else
                Toast.makeText(requireContext(), "تمام فیلد ها را پر کنید!!!", Toast.LENGTH_SHORT)
                    .show()


        }

        /*===============================btn add image in gallery=====================================*/
        img_add.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            if (!load_img_1)
                startActivityForResult(gallery, REQUEST_CODE_IMG_1)
            else if (!load_img_2)
                startActivityForResult(gallery, REQUEST_CODE_IMG_2)
            else if (!load_img_3)
                startActivityForResult(gallery, REQUEST_CODE_IMG_3)
            else
                Toast.makeText(
                    requireContext(),
                    "مجاز به انتخاب سه عکس هستید!!!",
                    Toast.LENGTH_LONG
                )
                    .show()

        }

        /*===========================delete select image =======================================*/
        delete_img_1.setOnClickListener {
            delete_img_1.visibility = View.GONE
            image_1.setImageResource(R.drawable.ic_image)
            load_img_1 = false
            post_img_1 = null
        }
        delete_img_2.setOnClickListener {
            delete_img_2.visibility = View.GONE
            image_2.setImageResource(R.drawable.ic_image)
            load_img_2 = false
            post_img_2 = null

        }
        delete_img_3.setOnClickListener {
            delete_img_3.visibility = View.GONE
            image_3.setImageResource(R.drawable.ic_image)
            load_img_3 = false
            post_img_3 = null

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val upload = UriToUploadable(requireActivity())
        /*   فایل با چه اسمی آپلود شود
          UUID.randomUUID(): فایل با اسم تصادفی آپلود شود*/

        if (resultCode == RESULT_OK) {

            imageUri = data?.data

            if (requestCode == REQUEST_CODE_IMG_1) {
                post_img_1 = upload.getUploaderFile(imageUri, "image1", "${UUID.randomUUID()}")
                image_1.setImageURI(imageUri)
                delete_img_1.visibility = View.VISIBLE
                load_img_1 = true
            }

            if (requestCode == REQUEST_CODE_IMG_2) {
                post_img_2 = upload.getUploaderFile(imageUri, "image2", "${UUID.randomUUID()}")
                image_2.setImageURI(imageUri)
                delete_img_2.visibility = View.VISIBLE
                load_img_2 = true
            }

            if (requestCode == REQUEST_CODE_IMG_3) {
                post_img_3 = upload.getUploaderFile(imageUri, "image3", "${UUID.randomUUID()}")
                image_3.setImageURI(imageUri)
                delete_img_3.visibility = View.VISIBLE
                load_img_3 = true
            }
        }
    }
}