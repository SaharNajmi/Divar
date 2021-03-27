package api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import model.AdModel
import model.LoginModel
import model.MSG
import model.UserIdModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object {
        const val BASE_URL = "http://192.168.1.103/divar/"
    }

    private var retrofit: Retrofit? = null
    private var request: ApiInterface

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        request = retrofit!!.create(ApiInterface::class.java)
    }

    fun sendActivationKey(mobile: String): Observable<LoginModel> {
        return request.sendActivationKey(mobile)
    }

    fun applyActivationKey(mobile: String, code: String): Observable<LoginModel> {
        return request.applyActivationKey(mobile, code)
    }

    fun getAllBanners(city: String, cate: String): Single<ArrayList<AdModel>> {
        return request.getAllBanner(city, cate)
    }

    fun getUserBanners(tell: String): Single<ArrayList<AdModel>> {
        return request.getUserBanner(tell)
    }

    fun addBanner(
        title: String,
        desc: String,
        price: String,
        userId: Int,
        city: String,
        cate: String,
        img1: String,
        img2: String,
        img3: String
    ): Observable<MSG> {
        return request.addBanner(title, desc, price, userId, city, cate, img1, img2, img3)
    }

    fun getUserIdFromPhoneNumber(tell: String): Single<UserIdModel> {
        return request.getUserIdFromTell(tell)
    }

    fun deleteBanner(id: Int): Single<MSG> {
        return request.deleteBanner(id)
    }
}