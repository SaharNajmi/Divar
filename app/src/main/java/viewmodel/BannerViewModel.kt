package viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BannerViewModel : ViewModel() {

    companion object {
        const val STATUS_LOGIN = true
    }

    //MutableLiveData
    private var listMutableLiveData = MutableLiveData<ArrayList<AdModel>>()
    private var mutableLiveDataApplyActivation = MutableLiveData<LoginModel>()
    private var mutableLiveDataSendActivation = MutableLiveData<LoginModel>()
    private var mutableLiveDataUserBanner = MutableLiveData<MSG>()
    private var mutableLiveDataId = MutableLiveData<UserIdModel>()
    private var mutableLiveDataPhoneNumber = MutableLiveData<PhoneModel>()

    //مدیریت درخواست رکوست به سمت سرور compositeDisposable
    private val compositeDisposable = CompositeDisposable()
    lateinit var api: ApiClient

    fun getListMutableLiveData(city: String, cate: String): MutableLiveData<ArrayList<AdModel>> {
        listMutableLiveData = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            //Schedulers: مدیریت ترد ها
            //newThread  روی ترد اصلی انجام نشه(در یک ترد جدید کار مورد نظر را انجام دهد )چون ترد اصلی بلاک میشه و اپ هنگ میکنه
            //mainThread نتیجه روی کدوم ترد برگردونه
            //روی یک ترد جدید نتیجه را روی ترد اصلی نشون میده
            api.getAllBanners(city, cate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<AdModel>>() {

                    override fun onSuccess(banner: ArrayList<AdModel>) {
                        listMutableLiveData.value = banner
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error getAllBanners!!!", e.toString())
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

                    override fun onSuccess(banner: ArrayList<AdModel>) {
                        listMutableLiveData.value = banner
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error getUserBanners!!!", e.toString())
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
                .subscribeWith(object : DisposableSingleObserver<LoginModel>() {
                    override fun onSuccess(t: LoginModel) {
                        mutableLiveDataSendActivation.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error send activeKey!!!", e.toString())
                    }
                })
        )
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
                .subscribeWith(object : DisposableSingleObserver<LoginModel>() {
                    override fun onSuccess(t: LoginModel) {
                        mutableLiveDataApplyActivation.value = t
                    }

                    override fun onError(e: Throwable) {

                        Log.d("error ActivationKey!!!", e.toString())
                    }


                })
        )
        return mutableLiveDataApplyActivation
    }

    /*==================================اضافه کردن آگهی=========================================*/
    fun addBanner(
        title: RequestBody,
        desc: RequestBody,
        price: RequestBody,
        userId: Int,
        city: RequestBody,
        cate: RequestBody,
        img1: MultipartBody.Part,
        img2: MultipartBody.Part,
        img3: MultipartBody.Part
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MSG>() {
                    override fun onSuccess(t: MSG) {
                        mutableLiveDataUserBanner.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error add banner!!!", e.toString())
                    }

                })
        )
        return mutableLiveDataUserBanner
    }


    /*==================================ویرایش آگهی=========================================*/
    fun editBanner(
        id: Int,
        title: RequestBody,
        desc: RequestBody,
        price: RequestBody,
        userId: Int,
        city: RequestBody,
        cate: RequestBody,
        img1: MultipartBody.Part,
        img2: MultipartBody.Part,
        img3: MultipartBody.Part
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MSG>() {
                    override fun onSuccess(t: MSG) {
                        mutableLiveDataUserBanner.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error edit banner!!!", e.toString())
                    }
                })
        )
        return mutableLiveDataUserBanner
    }

    /*==================گرفتن id کاربر از طریق گرفتن شماره موبایل========================*/
    fun getMutableLiveDataId(tell: String): MutableLiveData<UserIdModel> {
        mutableLiveDataId = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.getUserIdFromPhoneNumber(tell)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserIdModel>() {
                    override fun onSuccess(t: UserIdModel) {
                        mutableLiveDataId.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error get user id!!!", e.toString())
                    }
                })
        )
        return mutableLiveDataId
    }

    /*==================گرفتن شماره موبایل کاربر از طریق id آن========================*/
    fun getMutableLiveDataTell(id: Int): MutableLiveData<PhoneModel> {
        mutableLiveDataPhoneNumber = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.getPhoneNumberFromUserId(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhoneModel>() {
                    override fun onSuccess(t: PhoneModel) {
                        mutableLiveDataPhoneNumber.value = t
                    }

                    override fun onError(e: Throwable) {
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
                    override fun onSuccess(t: MSG) {
                        mutableLiveDataUserBanner.value = t
                    }

                    override fun onError(e: Throwable) {
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