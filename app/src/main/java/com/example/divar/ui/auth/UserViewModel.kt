package com.example.divar.ui.auth

import androidx.lifecycle.MutableLiveData
import com.example.divar.common.MyViewModel
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Login
import com.example.divar.data.model.Message
import com.example.divar.data.model.UserID
import com.example.divar.data.repository.LoginUpdate
import com.example.divar.data.repository.UserRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

class UserViewModel(
    private val userRepository: UserRepository
) : MyViewModel() {

    val userBanner = MutableLiveData<List<Advertise>>()

    //send ActivationKey to phoneNumber
    fun sendActivationKey(mobile: String): Single<Login> =
        userRepository.sendActivationKey(mobile)

    //Login by enter ActivationKey
    fun applyActivationKey(
        mobile: String,
        activation_key: String
    ): Single<Login> = userRepository.applyActivationKey(mobile, activation_key)

    val isSignIn: Boolean
        get() = LoginUpdate.login != false

    val phoneNumber: String
        get() = userRepository.getPhoneNumber()

    fun signOut() = userRepository.signOut()

    fun getUserBanners(phone: String): MutableLiveData<List<Advertise>> {
        userRepository.getUserBanner(phone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Advertise>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Advertise>) {
                    userBanner.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
        return userBanner
    }

    fun getUserId(tell: String): Single<UserID> = userRepository.getUserId(tell)

    fun refresh(phone: String) {
        getUserBanners(phone)
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
    ): Single<Message> = userRepository.addBanner(
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
    ): Single<Message> = userRepository.editBanner(
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
    ): Single<Message> = userRepository.deleteBanner(id)
}