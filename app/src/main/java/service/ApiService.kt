package service

import commom.BASE_URL
import data.model.AdModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //در تمام اپ های اندرویدی نوع رکوست ریسپانس اندرویدی single محسوب میشه یعنی اونایی که از نوع api تعریف میشن از این نوعن
    // Single  چون فقط یک ریسپانس از سرور برمیگرده( که یا موفقیت آمیز بوده یا نه) استفاده میکنیم
    //Observable اگر چند ریسپانس داشته باشیم به جای سینگل استفاده میکنیم: مثلا موقع دانلود فایل چون بخش خش دانلود میشه از این نوعه
    @GET("GetBanner.php")
    fun getAllBanner(
        @Query("city") city: String, @Query("category") cate: String
    ): Single<List<AdModel>>

}

fun createApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
