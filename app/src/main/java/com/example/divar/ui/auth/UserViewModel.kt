package com.example.divar.ui.auth

import androidx.lifecycle.MutableLiveData
import com.example.divar.commom.MyViewModel
import com.example.divar.data.model.AdModel
import com.example.divar.data.model.LoginModel
import com.example.divar.data.model.MSG
import com.example.divar.data.model.UserIdModel
import com.example.divar.data.repository.LoginUpdate
import com.example.divar.data.repository.UserDataRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

class UserViewModel(
    val userDataRepository: UserDataRepository
) : MyViewModel() {

    val userBannerLiveData = MutableLiveData<List<AdModel>>()


    //ارسال کد فعال سازی به شماره موبایل
    fun sendActivationKey(mobile: String): Single<LoginModel> =
        userDataRepository.sendActivationKey(mobile)

    //لاگین کردن با ورود کد تایید
    fun applyActivationKey(
        mobile: String,
        activation_key: String
    ): Single<LoginModel> = userDataRepository.applyActivationKey(mobile, activation_key)


    val isSignIn: Boolean
        get() = LoginUpdate.login != false

    val phoneNumber: String
        get() = userDataRepository.getPhoneNumber()

    fun signOut() = userDataRepository.signOut()

    fun getUserBanner(phone: String): MutableLiveData<List<AdModel>> {
        userDataRepository.getUserBanner(phone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<AdModel>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<AdModel>) {
                    userBannerLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
        return userBannerLiveData
    }

    fun getUserId(tell: String): Single<UserIdModel> = userDataRepository.getUserId(tell)

    fun refresh(phone: String) {
        getUserBanner(phone)
    }

    fun addBanner(
        title: RequestBody,
        description: RequestBody,
        price: RequestBody,
        userID: Int,
        city: RequestBody,
        category: RequestBody,
        postImage1: MultipartBody.Part,
        postImage2: MultipartBody.Part,
        postImage3: MultipartBody.Part
    ): Single<MSG> = userDataRepository.addBanner(
        title,
        description,
        price,
        userID,
        city,
        category,
        postImage1,
        postImage2,
        postImage3
    )

    fun editBanner(
        id: Int,
        title: RequestBody,
        description: RequestBody,
        price: RequestBody,
        userID: Int,
        city: RequestBody,
        category: RequestBody,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        image3: MultipartBody.Part
    ): Single<MSG> = userDataRepository.editBanner(
        id,
        title,
        description,
        price,
        userID,
        city,
        category,
        image1,
        image2,
        image3
    )

    fun deleteBanner(
        id: Int
    ): Single<MSG> = userDataRepository.deleteBanner(id)
}