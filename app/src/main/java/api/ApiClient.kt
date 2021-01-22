package api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import model.AdModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        const val BASE_URL = "http://192.168.1.103/divar/"
    }

    private var retrofit: Retrofit? = null
    private val request: ApiInterface

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        request = retrofit!!.create(ApiInterface::class.java)
    }

    fun getAllBanners(city:String,cate:String): Single<ArrayList<AdModel>> {
        //return request.getBanner()
        return request.getAllBanner(city,cate)
    }
}