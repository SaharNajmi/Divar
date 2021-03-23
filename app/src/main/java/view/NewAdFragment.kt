package view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_new_ad.*
import kotlinx.android.synthetic.main.fragment_new_ad.spinner_city
import kotlinx.android.synthetic.main.toolbar.*

class NewAdFragment : Fragment() {

    private var validate = true
    private val cate_base = HashMap<String, List<String>>()

    private val cityArray = listOf("کردستان", "تهران", "اردبیل")

    private val data: HashMap<String, List<String>>
        get() {
            val cate_0 = ArrayList<String>()
            cate_0.add("موبایل")
            cate_0.add("تبلت")
            cate_0.add("لپ تاپ")

            val cate_1 = ArrayList<String>()
            cate_1.add("رهن و اجاره")
            cate_1.add("خرید و فروش")

            val cate_2 = ArrayList<String>()
            cate_2.add("خودرو")
            cate_2.add("موتور سیکلت")
            cate_2.add("اجاره خودرو")
            cate_2.add("کشاورزی")

            cate_base["لوازم الکترونیکی"] = cate_0
            cate_base["املاک"] = cate_1
            cate_base["وسایل نقلیه"] = cate_2

            return cate_base
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_ad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*===============================add banner======================================*/
        btn_add_banner.setOnClickListener {

            if (txt_title.text.toString().trim().length >= 10) {
                title_layout.isErrorEnabled = false
            } else {

                title_layout.error = "عنوان آگهی باید حداقل 10 حرف باشد"
                title_layout.isErrorEnabled = true

                validate = false

            }

            if (txt_description.text.toString().trim().length >= 30) {
                description_layout.isErrorEnabled = false
            } else {
                description_layout.error = "توضیحات آگهی باید حداقل 30 حرف باشد";
                description_layout.isErrorEnabled = true
                validate = false
            }

            if (txt_price.text.toString().trim()
                    .isEmpty() || Integer.parseInt(txt_price.text.toString()) <= 0
            ) {
                price_layout.error = "قیمت وارد شده اشتباه است"
                price_layout.isErrorEnabled = true
                validate = false
            } else {
                price_layout.isErrorEnabled = false
            }
        }
    }
}