package api

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {
    @GET("GetBanner.php")
    fun getAllBanner(
        @Query("city") city: String, @Query("category") cate: String
    ): Single<ArrayList<AdModel>>

    @GET("GetUserBanners.php")
    fun getUserBanner(
        @Query("tell") tell: String
    ): Single<ArrayList<AdModel>>

    @FormUrlEncoded
    @POST("SendActivationKey.php")
    fun sendActivationKey(
        @Field("mobile") mobile: String
    ): Observable<LoginModel>

    @FormUrlEncoded
    @POST("ApplyActivationKey.php")
    fun applyActivationKey(
        @Field("mobile") mobile: String,
        @Field("activation_key") activation_key: String
    ): Observable<LoginModel>


    //استفاده از Retrofit برای آپلود فایل
    /*RequestBody:
    به جای نوع دادای رشته ای استفاده میکنیم تا مقادیر در دیتابیس داخل "" نشان داده نشود*/
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
    ): Observable<MSG>

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
    ): Observable<MSG>

    @GET("DeleteBanner.php")
    fun deleteBanner(
        @Query("id") id: Int
    ): Single<MSG>

    //گرفتن ای دی کاربر از طریق شماره موبایل
    @GET("getUserIdFromPhoneNumber.php")
    fun getUserIdFromTell(
        @Query("tell") tell: String
    ): Single<UserIdModel>


   /*  مان پیدا کردن شماره تلفن آگهی های بقیه- چون ای دی آنها موجود است ما جدول banner داریم
    پاما شماره موبایل آنها در  جدول user است که از طریق userId به آن میتوانیم دسترسی داشته باشیم*/

    //گرفتن شماره تلفن کاربر از طریق userId
    @GET("getPhoneNumberFromUserId.php")
    fun getTellFromUserId(
        @Query("userId") id: Int
    ): Single<PhoneModel>
}