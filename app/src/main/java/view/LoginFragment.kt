package view

import adapter.AdAdapter
import adapter.ItemOnClickListener
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.rec_my_ad
import model.AdModel
import model.LoginModel
import viewmodel.BannerViewModel


class LoginFragment : Fragment(), ItemOnClickListener {

    private lateinit var viewModel: BannerViewModel
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

        /*================اگر کاربر از قبل لاگین کرده بود آگهی های کاربر با همین شماره را نشان دهد============*/
        checkLogin()
        showAdUser()
        //خروج از حساب
        txt_log_out.setOnClickListener {
            rec_my_ad.visibility = View.GONE
            txt_log_out.visibility = View.GONE
            login_layout.visibility = View.VISIBLE
            pref.edit().putBoolean("login", false).apply()
        }

        /*====================ارسال کد چهار رقمی به شماره موبایل=====================*/
        submit_bt.setOnClickListener {
            var isValid = true
            if (!mobile_text.text.toString().trim()
                    .startsWith("09") || mobile_text.text.toString().length != 11
            ) {
                isValid = false
            }

            if (isValid) {
                val listMutableLiveData = viewModel.sendActivationKey(mobile_text.text.toString())

                listMutableLiveData.observe(
                    requireActivity(),
                    object : Observer<LoginModel> {
                        override fun onChanged(data: LoginModel?) {
                            Toast.makeText(
                                requireContext(),
                                data!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                activation_layout.visibility = View.VISIBLE
                login_layout.visibility = View.GONE
            } else {
                Toast.makeText(
                    requireContext(),
                    "اطلاعات وارد شده صحیح نیست",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        /*===============وارد کردن کد فعال سازی ارسال شده به شماره همراه=====================*/
        submit2_bt.setOnClickListener {
            val txtCode = activation_code.text.toString().trim()
            val txtTell = mobile_text.text.toString().trim()

            //ذخیره کردن شماره موایل
            val editor = pref.edit()
            editor?.putString("tell", txtTell)
            editor?.apply()

            if (txtCode.length == 4) {
                val listMutableLiveData = viewModel.applyActivationKey(txtTell, txtCode)
                listMutableLiveData.observe(
                    requireActivity(),
                    object : Observer<LoginModel> {
                        override fun onChanged(data: LoginModel?) {
                            Toast.makeText(
                                requireContext(),
                                data!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()

                            if (data.status) {

                                rec_my_ad.visibility = View.VISIBLE
                                txt_log_out.visibility = View.VISIBLE
                                activation_layout.visibility = View.GONE

                                showAdUser()

                                //هر سری کاربر لاگین نکند
                                val editor = pref?.edit()
                                editor?.putBoolean("login", true)
                                editor?.apply()
                            }
                        }
                    })
            } else
                Toast.makeText(
                    requireContext(),
                    "اطلاعات وارد شده صحیح نیست!!!",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }


    /*=========================== show ad user banners ===========================*/
    fun showAdUser() {
        val phone = pref.getString("tell", "")
        val listMutableLiveData: MutableLiveData<ArrayList<AdModel>> =
            viewModel.getListMutableLiveDataUserBanner(phone!!)

        listMutableLiveData.observe(requireActivity(), object : Observer<ArrayList<AdModel>> {
            override fun onChanged(t: ArrayList<AdModel>?) {
                val adapter = AdAdapter(requireContext(), t!!, this@LoginFragment)
                val manager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                rec_my_ad.layoutManager = manager
                rec_my_ad.adapter = adapter
            }
        })
    }

    private fun checkLogin() {
        if (pref.getBoolean("login", false)) {
            rec_my_ad.visibility = View.VISIBLE
            txt_log_out.visibility = View.VISIBLE
            login_layout.visibility = View.GONE
        }
    }

    override fun onItemClick(item: AdModel) {
        val go = Intent(context, DetailAdActivity::class.java)
        go.putExtra("id", item.id)
        go.putExtra("title", item.title)
        go.putExtra("description", item.description)
        go.putExtra("price", item.price)
        go.putExtra("city", item.city)
        go.putExtra("tell", item.userID)
        go.putExtra("category", item.category)
        go.putExtra("date", item.date)
        go.putExtra("status", item.status)
        go.putExtra("img1", item.img1)
        go.putExtra("img2", item.img2)
        go.putExtra("img3", item.img3)
        startActivity(go)
    }
}