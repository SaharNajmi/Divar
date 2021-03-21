package api

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import model.AdModel
import model.LoginModel
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