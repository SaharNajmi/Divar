package com.example.divar.data.repository

import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Chat
import com.example.divar.data.model.Login
import com.example.divar.data.model.Message
import com.example.divar.data.model.UserID
import com.example.divar.data.repository.source.UserDataSource
import com.example.divar.data.repository.source.local.UserLocalDataSource
import com.example.divar.data.repository.source.remote.UserRemoteDataSource
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserDataSource {
    override fun
            sendActivationKey(mobile: String): Single<Login> =
        userRemoteDataSource.sendActivationKey(mobile).doOnSuccess {
            Timber.i("ارسال کد تایید")
        }

    override fun applyActivationKey(mobile: String, activation_key: String): Single<Login> =
        userRemoteDataSource.applyActivationKey(mobile, activation_key).doOnSuccess {
            Timber.i("لاگین شد")
            onSuccessLogin(it, mobile)
        }

    private fun onSuccessLogin(login: Login, phone: String) {
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

    override fun getUserBanner(phoneNumber: String): Single<List<Advertise>> =
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
    ): Single<Message> = userRemoteDataSource.addBanner(
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
    ): Single<Message> = userRemoteDataSource.editBanner(
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

    override fun deleteBanner(id: Int): Single<Message> =
        userRemoteDataSource.deleteBanner(id).doOnSuccess { Timber.i(it.msg) }

    override fun getUserId(tell: String): Single<UserID> =
        userRemoteDataSource.getUserId(tell).doOnSuccess {
            Timber.i("user id: " + it.userId)
        }

    override fun getMessage(myPhone: String, bannerId: Int): Single<List<Chat>> =
        userRemoteDataSource.getMessage(myPhone, bannerId).doOnSuccess { Timber.i("my chat") }

}