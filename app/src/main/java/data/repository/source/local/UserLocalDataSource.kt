package data.repository.source.local

import android.content.SharedPreferences
import data.model.*
import data.repository.LoginUpdate
import data.repository.source.UserDataSource
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserLocalDataSource(val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun sendActivationKey(mobile: String): Single<LoginModel> {
        TODO("Not yet implemented")
    }

    override fun applyActivationKey(mobile: String, activation_key: String): Single<LoginModel> {
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

    override fun getUserBanner(phoneNumber: String): Single<List<AdModel>> {
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
    ): Single<MSG> {
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
    ): Single<MSG> {
        TODO("Not yet implemented")
    }

    override fun deleteBanner(id: Int): Single<MSG> {
        TODO("Not yet implemented")
    }

    override fun getUserId(tell: String): Single<UserIdModel> {
        TODO("Not yet implemented")
    }

    override fun getMessage(myPhone: String, bannerId: Int): Single<List<ChatList>> {
        TODO("Not yet implemented")
    }
}