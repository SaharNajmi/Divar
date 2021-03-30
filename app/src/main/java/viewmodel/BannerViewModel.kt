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
import model.MSG
import model.UserIdModel

class BannerViewModel : ViewModel() {

    companion object {
        const val STATUS_LOGIN = true
    }

    //MutableLiveData
    private var listMutableLiveData = MutableLiveData<ArrayList<AdModel>>()
    private var mutableLiveDataApplyActivation = MutableLiveData<LoginModel>()
    private var mutableLiveDataSendActivation = MutableLiveData<LoginModel>()
    private var mutableLiveDataUserBanner = MutableLiveData<MSG>()
    private var mutableLiveDataPhoneNumber = MutableLiveData<UserIdModel>()

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
        //Schedulers: مدیریت ترد ها
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
                .subscribe {
                    mutableLiveDataSendActivation.value = it
                })
        return mutableLiveDataSendActivation
    }

    /*=========================لاگین کردن با ورود کد تایید=================================*/
    fun applyActivationKey(mobile: String, code: String): MutableLiveData<LoginModel> {
        mutableLiveDataApplyActivation = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.applyActivationKey(mobile, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mutableLiveDataApplyActivation.value = it
                })
        return mutableLiveDataApplyActivation
    }

    /*==================================اضافه کردن آگهی=========================================*/
    fun addBanner(
        title: String,
        desc: String,
        price: String,
        userId: Int,
        city: String,
        cate: String,
        img1: String,
        img2: String,
        img3: String
    ): MutableLiveData<MSG> {
        mutableLiveDataUserBanner = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.addBanner(
                title,
                desc,
                price,
                userId,
                city,
                cate,
                img1,
                img2,
                img3
            ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe() {
                    mutableLiveDataUserBanner.value = it
                }
        )
        return mutableLiveDataUserBanner
    }


    /*==================================ویرایش آگهی=========================================*/
    fun editBanner(
        id: Int,
        title: String,
        desc: String,
        price: String,
        userId: Int,
        city: String,
        cate: String,
        img1: String,
        img2: String,
        img3: String
    ): MutableLiveData<MSG> {
        mutableLiveDataUserBanner = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.editBanner(
                id,
                title,
                desc,
                price,
                userId,
                city,
                cate,
                img1,
                img2,
                img3
            ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe() {
                    mutableLiveDataUserBanner.value = it
                }
        )
        return mutableLiveDataUserBanner
    }

    /*==================گرفتن id کاربر از طریق گرفتن شماره موبایل========================*/
    fun getMutableLiveDataTell(tell: String): MutableLiveData<UserIdModel> {
        mutableLiveDataPhoneNumber = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.getUserIdFromPhoneNumber(tell)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserIdModel>() {
                    override fun onSuccess(t: UserIdModel?) {
                        mutableLiveDataPhoneNumber.value = t
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("error live data!!!", e.toString())
                    }
                })
        )
        return mutableLiveDataPhoneNumber
    }

    fun deleteBanner(id: Int): MutableLiveData<MSG> {
        mutableLiveDataUserBanner = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.deleteBanner(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MSG>() {
                    override fun onSuccess(t: MSG?) {
                        mutableLiveDataUserBanner.value = t
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("error delete!!!", e.toString())
                    }
                })
        )
        return mutableLiveDataUserBanner
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}