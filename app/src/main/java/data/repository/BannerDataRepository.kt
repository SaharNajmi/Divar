package data.repository

import data.model.AdModel
import data.repository.source.BannerDataSource
import data.repository.source.local.BannerLocalDataSource
import data.repository.source.remote.BannerRemoteDataSource
import io.reactivex.Single

class BannerDataRepository(
    val bannerLocalDataSource: BannerLocalDataSource,
    val bannerRemoteDataSource: BannerRemoteDataSource
) : BannerDataSource {

    override fun getAllBanner(city: String, category: String): Single<List<AdModel>> =
        bannerRemoteDataSource.getAllBanner(city, category)
}