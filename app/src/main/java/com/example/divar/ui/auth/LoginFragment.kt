package com.example.divar.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import com.example.divar.common.Constants.EXTRA_KEY_DATA
import com.example.divar.common.ItemOnClickListener
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Login
import com.example.divar.ui.home.BannerAdapter
import com.example.divar.ui.home.DetailAdActivity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginFragment : Fragment(), ItemOnClickListener {

    private val userViewModel: UserViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()

        showAdUser()

        txt_log_out.setOnClickListener {
            rec_my_ad.visibility = View.GONE
            txt_log_out.visibility = View.GONE
            login_layout.visibility = View.VISIBLE
            userViewModel.signOut()
        }

        submit_bt.setOnClickListener {
            var isValid = true
            if (!mobile_text.text.toString().trim()
                    .startsWith("09") || mobile_text.text.toString().length != 11
            ) {
                isValid = false
            }

            if (isValid) {
                userViewModel.sendActivationKey(mobile_text.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Login> {
                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onSuccess(t: Login) {
                            Toast.makeText(
                                requireContext(),
                                t.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onError(e: Throwable) {
                            Timber.e(e)
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

        submit2_bt.setOnClickListener {
            val txtCode = activation_code.text.toString().trim()
            val txtTell = mobile_text.text.toString().trim()

            if (txtCode.length == 4) {

                userViewModel.applyActivationKey(txtTell, txtCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Login> {
                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onSuccess(t: Login) {
                            Toast.makeText(
                                requireContext(),
                                t.msg,
                                Toast.LENGTH_SHORT
                            ).show()

                            if (t.status) {

                                rec_my_ad.visibility = View.VISIBLE
                                txt_log_out.visibility = View.VISIBLE
                                activation_layout.visibility = View.GONE

                                showAdUser()
                            }
                        }

                        override fun onError(e: Throwable) {
                            Timber.i(e)
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

    @SuppressLint("SetTextI18n")
    fun showAdUser() {
        txt_log_out.text = " خروج از حساب ${userViewModel.phoneNumber}"

        userViewModel.getUserBanners(userViewModel.phoneNumber)
            .observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    rec_my_ad.adapter = BannerAdapter(it as ArrayList<Advertise>, this)
                    rec_my_ad.layoutManager =
                        GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

                    rec_my_ad.visibility = View.VISIBLE
                    emptyLayout.visibility = View.GONE

                } else {
                    rec_my_ad.visibility = View.GONE
                    emptyLayout.visibility = View.VISIBLE
                    emptyLayout.txtEmpty.text = getString(R.string.emptyBanner)
                }
            }
    }

    private fun checkLogin() {
        if (userViewModel.isSignIn) {
            rec_my_ad.visibility = View.VISIBLE
            txt_log_out.visibility = View.VISIBLE
            login_layout.visibility = View.GONE
        }
    }

    override fun onItemClick(item: Advertise) {
        startActivity(Intent(requireContext(), DetailAdActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, item)
        })
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        userViewModel.refresh(userViewModel.phoneNumber)
    }
}