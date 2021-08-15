package service

import commom.BASE_URL
import data.model.AdModel
import data.model.LoginModel
import data.model.MSG
import data.model.UserIdModel
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    //در تمام اپ های اندرویدی نوع رکوست ریسپانس اندرویدی single محسوب میشه یعنی اونایی که از نوع api تعریف میشن از این نوعن
    // Single  چون فقط یک ریسپانس از سرور برمیگرده( که یا موفقیت آمیز بوده یا نه) استفاده میکنیم
    //Observable اگر چند ریسپانس داشته باشیم به جای سینگل استفاده میکنیم: مثلا موقع دانلود فایل چون بخش خش دانلود میشه از این نوعه
    @GET("GetBanner.php")
    fun getAllBanner(
        @Query("city") city: String, @Query("category") cate: String
    ): Single<List<AdModel>>

    @FormUrlEncoded
    @POST("SendActivationKey.php")
    fun sendActivationKey(
        @Field("mobile") mobile: String
    ): Single<LoginModel>

    @FormUrlEncoded
    @POST("ApplyActivationKey.php")
    fun applyActivationKey(
        @Field("mobile") mobile: String,
        @Field("activation_key") activation_key: String
    ): Single<LoginModel>

    @GET("GetUserBanners.php")
    fun getUserBanner(
        @Query("tell") tell: String
    ): Single<List<AdModel>>

    // به جای نوع دادای رشته ای استفاده میکنیم تا مقادیر در دیتابیس داخل "" نشان داده نشود RequestBody
    @Multipart
    @POST("AddBanner.php")
    fun addBanner(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("userID") userID: Int,
        @Part("city") city: RequestBody,
        @Part("category") category: RequestBody,
        @Part postImage1: MultipartBody.Part,
        @Part postImage2: MultipartBody.Part,
        @Part postImage3: MultipartBody.Part
    ): Single<MSG>

    @Multipart
    @POST("EditBanner.php")
    fun editBanner(
        @Part("id") id: Int,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("userID") userID: Int,
        @Part("city") city: RequestBody,
        @Part("category") category: RequestBody,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part
    ): Single<MSG>

    @GET("DeleteBanner.php")
    fun deleteBanner(
        @Query("id") id: Int
    ): Single<MSG>

    //گرفتن ای دی کاربر از طریق شماره موبایل
    @GET("getUserIdFromPhoneNumber.php")
    fun getUserId(
        @Query("tell") tell: String
    ): Single<UserIdModel>

}

fun createApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
