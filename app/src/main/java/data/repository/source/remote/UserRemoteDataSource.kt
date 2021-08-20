package data.repository.source.remote

import data.model.*
import data.repository.source.UserDataSource
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import service.ApiService

class UserRemoteDataSource(val apiService: ApiService) : UserDataSource {
    override fun sendActivationKey(mobile: String): Single<LoginModel> =
        apiService.sendActivationKey(mobile)

    override fun applyActivationKey(mobile: String, activation_key: String): Single<LoginModel> =
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

    override fun getUserBanner(phoneNumber: String): Single<List<AdModel>> =
        apiService.getUserBanner(phoneNumber)

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
    ): Single<MSG> = apiService.addBanner(
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
    ): Single<MSG> = apiService.editBanner(
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

    override fun deleteBanner(id: Int): Single<MSG> = apiService.deleteBanner(id)

    override fun getUserId(tell: String): Single<UserIdModel> = apiService.getUserId(tell)

    override fun getMessage(myPhone: String, bannerId: Int): Single<List<ChatList>> =
        apiService.getMessages(myPhone, bannerId)

}