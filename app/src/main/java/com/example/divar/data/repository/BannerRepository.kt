package com.example.divar.data.repository

import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.repository.source.BannerDataSource
import com.example.divar.data.repository.source.local.BannerLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class BannerRepository(
    private val bannerLocalDataSource: BannerLocalDataSource,
    private val bannerRemoteDataSource: BannerDataSource
) : BannerDataSource {
    override fun getBanners(city: String, category: String): Single<List<Advertise>> =
        bannerLocalDataSource.getFavorites().flatMap { fav ->
            bannerRemoteDataSource.getBanners(city, category).doOnSuccess {
                //get all favorite Ids
                val favIds = fav.map { advertise ->
                    advertise.id
                }
                //if favId == bannerId from server -> there is item in favorite
                it.forEach { banner ->
                    if (favIds.contains(banner.id))
                        banner.favorite = true
                }
            }
        }

    override fun getFavorites(): Single<List<Advertise>> = bannerLocalDataSource.getFavorites()

    override fun addToFavorites(banner: Advertise): Completable =
        bannerLocalDataSource.addToFavorites(banner)

    override fun deleteFromFavorites(banner: Advertise): Completable =
        bannerLocalDataSource.deleteFromFavorites(banner)
}