package view

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_login.*
import model.LoginModel
import viewmodel.BannerViewModel


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

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
                                data.toString(),
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
}