package com.example.divar.data.repository.source

import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Chat
import com.example.divar.data.model.Login
import com.example.divar.data.model.Message
import com.example.divar.data.model.UserID
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserDataSource {

    fun sendActivationKey(mobile: String): Single<Login>

    fun applyActivationKey(mobile: String, activation_key: String): Single<Login>

    fun saveLogin(login: Boolean)

    fun checkLogin()

    fun signOut()

    fun savePhoneNumber(phoneNumber: String)

    fun getPhoneNumber(): String

    fun getUserBanner(phoneNumber: String): Single<List<Advertise>>

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
    ): Single<Message>

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
    ): Single<Message>

    fun deleteBanner(
        id: Int
    ): Single<Message>

    fun getUserId(tell: String): Single<UserID>

    fun getMessage(myPhone: String, bannerId: Int): Single<List<Chat>>

}