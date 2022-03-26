package com.example.divar.data.repository.source.remote

import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Chat
import com.example.divar.data.model.Login
import com.example.divar.data.model.Message
import com.example.divar.data.model.UserID
import com.example.divar.data.repository.source.UserDataSource
import com.example.divar.service.ApiService
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRemoteDataSource(private val apiService: ApiService) : UserDataSource {
    override fun sendActivationKey(mobile: String): Single<Login> =
        apiService.sendActivationKey(mobile)

    override fun applyActivationKey(mobile: String, activation_key: String): Single<Login> =
        apiService.applyActivationKey(mobile, activation_key)

    override fun saveLogin(login: Boolean) {
        TODO("Not yet implemented")
    }

    override fun checkLogin() {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun savePhoneNumber(phoneNumber: String) {
        TODO("Not yet implemented")
    }

    override fun getPhoneNumber(): String {
        TODO("Not yet implemented")
    }

    override fun getUserBanner(phoneNumber: String): Single<List<Advertise>> =
        apiService.getUserBanners(phoneNumber)

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
    ): Single<Message> = apiService.addBanner(
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
    ): Single<Message> = apiService.editBanner(
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

    override fun deleteBanner(id: Int): Single<Message> = apiService.deleteBanner(id)

    override fun getUserId(tell: String): Single<UserID> = apiService.getUserId(tell)

    override fun getMessage(myPhone: String, bannerId: Int): Single<List<Chat>> =
        apiService.getMessages(myPhone, bannerId)

}