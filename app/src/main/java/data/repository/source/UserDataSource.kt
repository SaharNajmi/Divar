package data.repository.source

import data.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserDataSource {

    fun sendActivationKey(mobile: String): Single<LoginModel>

    fun applyActivationKey(mobile: String, activation_key: String): Single<LoginModel>

    fun saveLogin(login: Boolean)

    fun checkLogin()

    fun signOut()

    fun savePhoneNumber(phoneNumber: String)

    fun getPhoneNumber(): String

    fun getUserBanner(phoneNumber: String): Single<List<AdModel>>

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
    ): Single<MSG>

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
    ): Single<MSG>

    fun deleteBanner(
        id: Int
    ): Single<MSG>

    fun getUserId(tell: String): Single<UserIdModel>

    fun getMessage(myPhone: String, bannerId: Int): Single<List<ChatList>>

}