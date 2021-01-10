package api

import io.reactivex.rxjava3.core.Single
import model.AdModel
import retrofit2.http.GET

interface ApiInterface {
    @GET("GetAllBanners.php")
    fun getBanner():Single<ArrayList<AdModel>>
}