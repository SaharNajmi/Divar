package api

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import model.AdModel
import model.LoginModel
import model.MSG
import model.UserIdModel
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

    @FormUrlEncoded
    @POST("AddBanner.php")
    fun addBanner(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("price") price: String,
        @Field("userID") userID: Int,
        @Field("city") city: String,
        @Field("category") category: String,
        @Field("img1") img1: String,
        @Field("img2") img2: String,
        @Field("img3") img3: String
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


    /*    @GET("DeleteCart.php")
    fun deleteCart(
        @Query("id") productID: Int
    ): Call<MSG>

    //گرفتن ای دی کاربر از طریق یوزرنیم
    @GET("getUserIdFromUsername.php")
    fun getUserIdFromUsername(
        @Query("username") username: String
    ): Call<UserIdModel>

    @FormUrlEncoded
    @POST("editUser.php")
    fun editUser(
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("newPassword") password: String
    ): Call<MSG>*/
}