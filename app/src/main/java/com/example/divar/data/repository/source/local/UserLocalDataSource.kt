package com.example.divar.data.repository.source.local

import android.content.SharedPreferences
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Chat
import com.example.divar.data.model.Login
import com.example.divar.data.model.Message
import com.example.divar.data.model.UserID
import com.example.divar.data.repository.LoginUpdate
import com.example.divar.data.repository.source.UserDataSource
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) : UserDataSource {

    override fun sendActivationKey(mobile: String): Single<Login> {
        TODO("Not yet implemented")
    }

    override fun applyActivationKey(mobile: String, activation_key: String): Single<Login> {
        TODO("Not yet implemented")
    }

    override fun saveLogin(login: Boolean) = sharedPreferences.edit().apply {
        putBoolean("login", login)
    }.apply()

    override fun checkLogin() {
        //sharedPreferences.getBoolean("login", false)
        LoginUpdate.update(sharedPreferences.getBoolean("login", false))
    }

    override fun signOut() = sharedPreferences.edit().apply {
        clear()
    }.apply()

    override fun savePhoneNumber(phoneNumber: String) = sharedPreferences.edit().apply {
        putString("tell", phoneNumber)
    }.apply()


    override fun getPhoneNumber(): String = sharedPreferences.getString("tell", "") ?: ""

    override fun getUserBanner(phoneNumber: String): Single<List<Advertise>> {
        TODO("Not yet implemented")
    }

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
    ): Single<Message> {
        TODO("Not yet implemented")
    }

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
    ): Single<Message> {
        TODO("Not yet implemented")
    }

    override fun deleteBanner(id: Int): Single<Message> {
        TODO("Not yet implemented")
    }

    override fun getUserId(tell: String): Single<UserID> {
        TODO("Not yet implemented")
    }

    override fun getMessage(myPhone: String, bannerId: Int): Single<List<Chat>> {
        TODO("Not yet implemented")
    }
}