package com.example.divar.data.repository.source.remote

import com.example.divar.data.model.AdModel
import com.example.divar.data.repository.source.BannerDataSource
import com.example.divar.service.ApiService
import io.reactivex.Completable
import io.reactivex.Single

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