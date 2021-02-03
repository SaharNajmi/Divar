package viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import api.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import model.AdModel
import model.LoginModel

class BannerViewModel : ViewModel() {

    companion object {
        const val STATUS_LOGIN = true
    }

    //MutableLiveData
    private var listMutableLiveData = MutableLiveData<ArrayList<AdModel>>()
    private var mutableLiveDataApplyActivation = MutableLiveData<LoginModel>()
    private var mutableLiveDataSendActivation = MutableLiveData<LoginModel>()

    //مدیریت درخواست رکوست به سمت سرور compositeDisposable
    private val compositeDisposable = CompositeDisposable()
    lateinit var api: ApiClient

    fun getListMutableLiveData(city: String, cate: String): MutableLiveData<ArrayList<AdModel>> {
        listMutableLiveData = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.getAllBanners(city, cate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<AdModel>>() {
                    override fun onSuccess(banner: ArrayList<AdModel>?) {
                        listMutableLiveData.value = banner
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("error live data!!!", e.toString())
                    }
                })
        )
        return listMutableLiveData
    }

    fun getListMutableLiveDataUserBanner(tell: String): MutableLiveData<ArrayList<AdModel>> {
        listMutableLiveData = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.getUserBanners(tell)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<AdModel>>() {
                    override fun onSuccess(banner: ArrayList<AdModel>?) {
                        listMutableLiveData.value = banner
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("error live data!!!", e.toString())
                    }
                })
        )
        return listMutableLiveData
    }


    /*====================ارسال کد فعال سازی به شماره موبایل=================================*/
    fun sendActivationKey(mobile: String): MutableLiveData<LoginModel> {
        mutableLiveDataSendActivation = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.sendActivationKey(mobile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe{
                    mutableLiveDataSendActivation.value = it
                    Log.d("sssssssss", it.toString())

                })
            return mutableLiveDataSendActivation
    }

    /*=========================لاگین کردن با ورود کد تایید=================================*/
    fun applyActivationKey(mobile: String, code: String): MutableLiveData<LoginModel> {
        mutableLiveDataApplyActivation = MutableLiveData()
        api = ApiClient()
        api.applyActivationKey(mobile, code)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mutableLiveDataApplyActivation.value = it
            }
        return mutableLiveDataApplyActivation
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}