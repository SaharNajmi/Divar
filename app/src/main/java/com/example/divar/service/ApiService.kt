package com.example.divar.service

import com.example.divar.common.Constants.BASE_URL
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.model.Chat
import com.example.divar.data.model.Login
import com.example.divar.data.model.Message
import com.example.divar.data.model.UserID
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("GetBanner.php")
    fun getBanners(
        @Query("city") city: String, @Query("category") cate: String
    ): Single<List<Advertise>>

    @FormUrlEncoded
    @POST("SendActivationKey.php")
    fun sendActivationKey(
        @Field("mobile") mobile: String
    ): Single<Login>

    @FormUrlEncoded
    @POST("ApplyActivationKey.php")
    fun applyActivationKey(
        @Field("mobile") mobile: String,
        @Field("activation_key") activation_key: String
    ): Single<Login>

    @GET("GetUserBanners.php")
    fun getUserBanners(
        @Query("tell") tell: String
    ): Single<List<Advertise>>

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
    ): Single<Message>

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
    ): Single<Message>

    @GET("DeleteBanner.php")
    fun deleteBanner(
        @Query("id") id: Int
    ): Single<Message>

    @GET("getUserIdFromPhoneNumber.php")
    fun getUserId(
        @Query("tell") tell: String
    ): Single<UserID>

    @GET("GetMessages.php")
    fun getMessages(
        @Query("MyPhone") myPhone: String,
        @Query("bannerID") bannerID: Int
    ): Single<List<Chat>>
}

fun createApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
