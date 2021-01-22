package api

import io.reactivex.rxjava3.core.Single
import model.AdModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("GetBanner.php")
    fun getAllBanner(
        @Query("city") city: String, @Query("category") cate: String
    ): Single<ArrayList<AdModel>>
}