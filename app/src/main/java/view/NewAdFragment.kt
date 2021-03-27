package view

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_new_ad.*
import model.ListCity
import model.MSG
import model.UserIdModel
import viewmodel.BannerViewModel

class NewAdFragment : Fragment() {
    private var validate1 = false
    private var validate2 = false
    private var validate3 = false
    private var validate4 = false
    private var menuItems: ListCity? = null
    private var selectedPositionCateBase: Int? = null
    private var selectedPositionSubCate: Int? = null
    var userId: Int? = null
    private lateinit var pref: SharedPreferences
    private lateinit var viewModel: BannerViewModel

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

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

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
        var category = ""
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

        val idUser = viewModel.getMutableLiveDataTell(phone!!)
        idUser.observe(requireActivity(), object : Observer<UserIdModel> {
            override fun onChanged(t: UserIdModel?) {
                userId = t!!.id

            }
        })

        /*===============================button submit add banner======================================*/
        btn_add_banner.setOnClickListener {

            selectedPositionSubCate = spinner_cate_sub.selectedItemPosition!!
            selectedPositionCateBase = selectedPositionCateBase?.plus(1)
            selectedPositionSubCate = selectedPositionSubCate?.plus(1)
            category = "$selectedPositionCateBase,$selectedPositionSubCate"

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
                val title = txt_title.text.toString()
                val desc = txt_description.text.toString()
                val price = txt_price.text.toString()
                val city = exposed_dropdown.text.toString()
                val msgAddBanner =
                    viewModel.addBanner(title, desc, price, userId!!, city, category, "", "", "")
                msgAddBanner.observe(requireActivity(), object : Observer<MSG> {
                    override fun onChanged(t: MSG?) {
                        Toast.makeText(requireContext(), t!!.msg, Toast.LENGTH_LONG).show()
                    }
                })

            } else
                Toast.makeText(requireContext(), "تمام فیلد ها پر کنید!!!", Toast.LENGTH_SHORT)
                    .show()


        }
    }
}