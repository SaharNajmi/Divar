package com.example.divar.data.repository

import com.example.divar.data.model.*
import com.example.divar.data.repository.source.UserDataSource
import com.example.divar.data.repository.source.local.UserLocalDataSource
import com.example.divar.data.repository.source.remote.UserRemoteDataSource
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

class UserDataRepository(
    val userRemoteDataSource: UserRemoteDataSource,
    val userLocalDataSource: UserLocalDataSource
) : UserDataSource {
    override fun
            sendActivationKey(mobile: String): Single<LoginModel> =
        userRemoteDataSource.sendActivationKey(mobile).doOnSuccess {
            Timber.i("ارسال کد تایید")
        }

    override fun applyActivationKey(mobile: String, activation_key: String): Single<LoginModel> =
        userRemoteDataSource.applyActivationKey(mobile, activation_key).doOnSuccess {
            Timber.i("لاگین شد")
            //وقتی ورود با موفقیت انجام شد شماره موبایل را ذخیره و وضعیت لاگین را true کند تا هر سری دوباره لاگین نکند
            onSuccessLogin(it, mobile)
        }

    fun onSuccessLogin(login: LoginModel, phone: String) {
        LoginUpdate.update(login.status)
        userLocalDataSource.saveLogin(login.status)
        userLocalDataSource.savePhoneNumber(phone)
    }

    override fun saveLogin(login: Boolean) {
        userLocalDataSource.saveLogin(login)
    }

    override fun checkLogin() {
        userLocalDataSource.checkLogin()
    }

    override fun signOut() {
        LoginUpdate.update(false)
        userLocalDataSource.signOut()
    }

    override fun savePhoneNumber(phoneNumber: String) =
        userLocalDataSource.savePhoneNumber(phoneNumber)

    override fun getPhoneNumber(): String =
        userLocalDataSource.getPhoneNumber()

    override fun getUserBanner(phoneNumber: String): Single<List<AdModel>> =
        userRemoteDataSource.getUserBanner(phoneNumber).doOnSuccess { Timber.i("get use banner") }

    override fun addBanner(
        title: RequestBody,
        description: RequestBody,
        price: RequestBody,
        userID: Int,
        city: RequestBody,
        category: RequestBody,
        postImage1: MultipartBody.Part,
        postImage2: MultipartBody.Part,
        postImage3: MultipartBody.Part
    ): Single<MSG> = userRemoteDataSource.addBanner(
        title,
        description,
        price,
        userID,
        city,
        category,
        postImage1,
        postImage2,
        postImage3
    ).doOnSuccess { Timber.i(it.msg) }

    override fun editBanner(
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
    ): Single<MSG> = userRemoteDataSource.editBanner(
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
    ).doOnSuccess { Timber.i(it.msg) }

    override fun deleteBanner(id: Int): Single<MSG> =
        userRemoteDataSource.deleteBanner(id).doOnSuccess { Timber.i(it.msg) }

    override fun getUserId(tell: String): Single<UserIdModel> =
        userRemoteDataSource.getUserId(tell).doOnSuccess {
            Timber.i("user id: " + it.id)
        }

    override fun getMessage(myPhone: String, bannerId: Int): Single<List<ChatList>> =
        userRemoteDataSource.getMessage(myPhone, bannerId).doOnSuccess { Timber.i("my chat") }

}