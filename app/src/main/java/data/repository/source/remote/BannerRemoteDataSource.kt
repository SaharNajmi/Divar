package data.repository.source.remote

import data.model.AdModel
import data.repository.source.BannerDataSource
import io.reactivex.Completable
import io.reactivex.Single
import service.ApiService

class BannerRemoteDataSource(val apiService: ApiService) : BannerDataSource {
    override fun getAllBanner(city: String, category: String): Single<List<AdModel>> =
        apiService.getAllBanner(city, category)

    override fun getFavorite(): Single<List<AdModel>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(banner: AdModel): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(banner: AdModel): Completable {
        TODO("Not yet implemented")
    }
}