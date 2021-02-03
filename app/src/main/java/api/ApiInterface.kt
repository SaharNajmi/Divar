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
}