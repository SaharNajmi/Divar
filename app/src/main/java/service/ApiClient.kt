package service

import data.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        const val BASE_URL = "http://192.168.1.102/divar/"
    }

    private var retrofit: Retrofit? = null
    private var request: ApiInterface

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        request = retrofit!!.create(ApiInterface::class.java)
    }

    fun sendActivationKey(mobile: String): Single<LoginModel> {
        return request.sendActivationKey(mobile)
    }

    fun applyActivationKey(mobile: String, code: String): Single<LoginModel> {
        return request.applyActivationKey(mobile, code)
    }

    fun getAllBanners(city: String, cate: String): Single<ArrayList<AdModel>> {
        return request.getAllBanner(city, cate)
    }

    fun getUserBanners(tell: String): Single<ArrayList<AdModel>> {
        return request.getUserBanner(tell)
    }

    fun addBanner(
        title: RequestBody,
        desc: RequestBody,
        price: RequestBody,
        userId: Int,
        city: RequestBody,
        cate: RequestBody,
        img1: MultipartBody.Part,
        img2: MultipartBody.Part,
        img3: MultipartBody.Part
    ): Single<MSG> {
        return request.addBanner(title, desc, price, userId, city, cate, img1, img2, img3)
    }

    fun editBanner(
        id: Int,
        title: RequestBody,
        desc: RequestBody,
        price: RequestBody,
        userId: Int,
        city: RequestBody,
        cate: RequestBody,
        img1: MultipartBody.Part,
        img2: MultipartBody.Part,
        img3: MultipartBody.Part
    ): Single<MSG> {
        return request.editBanner(id, title, desc, price, userId, city, cate, img1, img2, img3)
    }

    fun getUserIdFromPhoneNumber(tell: String): Single<UserIdModel> {
        return request.getUserIdFromTell(tell)
    }

    fun getPhoneNumberFromUserId(id: Int): Single<PhoneModel> {
        return request.getTellFromUserId(id)
    }

    fun deleteBanner(id: Int): Single<MSG> {
        return request.deleteBanner(id)
    }
}