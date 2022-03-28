package com.example.divar.data.repository.source.remote

import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.repository.source.BannerDataSource
import com.example.divar.service.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class BannerRemoteDataSource(private val apiService: ApiService) : BannerDataSource {
    override fun getBanners(city: String, category: String): Single<List<Advertise>> =
        apiService.getBanners(city, category)

    override fun getFavorites(): Single<List<Advertise>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(banner: Advertise): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(banner: Advertise): Completable {
        TODO("Not yet implemented")
    }
}